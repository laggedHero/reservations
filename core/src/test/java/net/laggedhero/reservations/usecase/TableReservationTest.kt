package net.laggedhero.reservations.usecase

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.data.table.TableRepository
import net.laggedhero.reservations.time.TimeProvider
import net.laggedhero.reservations.usecase.TableReservation.TableReservationState
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyList
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TableReservationTest {

    @Mock
    lateinit var localTableRepository: TableRepository
    @Mock
    lateinit var remoteTableRepository: TableRepository
    @Mock
    lateinit var timeProvider: TimeProvider

    lateinit var tableReservation: TableReservation

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        tableReservation =
                TableReservation(localTableRepository, remoteTableRepository, timeProvider)
    }

    // region reserve
    @Test
    fun `reserve should error when table is not available`() {
        val table = Table(1, false, 10_000)

        tableReservation.reserve(table)
            .test()
            .assertError(Throwable::class.java)
    }

    @Test
    fun `reserve should update table with the latest time`() {
        val table = Table(1, true, 10_000)

        `when`(timeProvider.currentTimeMillis())
            .thenReturn(20_000)
        `when`(localTableRepository.saveAll(anyList()))
            .thenReturn(Completable.complete())

        tableReservation.reserve(table)
            .test()
            .assertComplete()

        verify(timeProvider, times(1))
            .currentTimeMillis()
        verify(localTableRepository, times(1))
            .saveAll(listOf(table.copy(timestamp = 20_000)))
    }
    // endregion reserve

    // region fetch
    @Test
    fun `fetch should return an initial load state`() {
        `when`(localTableRepository.getAll())
            .thenReturn(Single.never())

        tableReservation.fetch()
            .test()
            .assertNotTerminated()
            .assertValue(TableReservationState())
    }

    @Test
    fun `fetch should fetch from remote and cache when local is empty`() {
        val tables = getTables(listOf(true, false), 10_000)

        `when`(localTableRepository.getAll())
            .thenReturn(Single.just(listOf()))
        `when`(localTableRepository.saveAll(anyList()))
            .thenReturn(Completable.complete())
        `when`(remoteTableRepository.getAll())
            .thenReturn(Single.just(tables))

        tableReservation.fetch()
            .test()
            .assertComplete()
            .assertValues(
                TableReservationState(),
                TableReservationState(tables, false)
            )

        verify(remoteTableRepository, times(1))
            .getAll()
        verify(localTableRepository, times(1))
            .saveAll(tables)
    }

    @Test
    fun `fetch should fetch from remote and merge with cache when local is not empty`() {
        val localTables = getTables(listOf(true, false, true, true), 10_000)
        val remoteTables = getTables(listOf(true, false, false, false), 20_000)
        val mergedTables = localTables.subList(0, 1) + remoteTables.subList(1, 4)

        `when`(localTableRepository.getAll())
            .thenReturn(Single.just(localTables))
            .thenReturn(Single.just(mergedTables))
        `when`(localTableRepository.saveAll(anyList()))
            .thenReturn(Completable.complete())
        `when`(remoteTableRepository.getAll())
            .thenReturn(Single.just(remoteTables))

        tableReservation.fetch()
            .test()
            .assertComplete()
            .assertValues(
                TableReservationState(),
                TableReservationState(mergedTables, false)
            )

        verify(remoteTableRepository, times(1))
            .getAll()
        verify(localTableRepository, times(2))
            .getAll()
        verify(localTableRepository, times(1))
            .saveAll(remoteTables.subList(1, 4))
    }
    // endregion fetch

    private fun getTables(availabilities: List<Boolean>, timestamp: Long): List<Table> {
        val tables = mutableListOf<Table>()
        availabilities.forEachIndexed { index, isAvailable ->
            tables.add(Table(index, isAvailable, timestamp))
        }
        return tables
    }
}