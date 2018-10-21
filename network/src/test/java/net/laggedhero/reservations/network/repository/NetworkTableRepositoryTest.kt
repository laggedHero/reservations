package net.laggedhero.reservations.network.repository

import io.reactivex.Single
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.network.api.ReservationApi
import net.laggedhero.reservations.time.TimeProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NetworkTableRepositoryTest {

    @Mock
    lateinit var reservationApi: ReservationApi
    @Mock
    lateinit var timeProvider: TimeProvider

    lateinit var repository: NetworkTableRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = NetworkTableRepository(reservationApi, timeProvider)
    }

    @Test
    fun `getAll returns a mapped api object to core object`() {
        val availabilities = listOf(true, false, false, true)

        `when`(reservationApi.getTables())
            .thenReturn(Single.just(availabilities))
        `when`(timeProvider.currentTimeMillis())
            .thenReturn(10_000)

        repository.getAll()
            .test()
            .assertComplete()
            .assertValue(getTables(availabilities, 10_000))
    }

    @Test
    fun `saveAll completes successfully`() {
        repository.saveAll(listOf())
            .test()
            .assertComplete()
    }

    private fun getTables(availabilities: List<Boolean>, timestamp: Long): List<Table> {
        val tables = mutableListOf<Table>()
        availabilities.forEachIndexed { index, isAvailable ->
            tables.add(Table(index, isAvailable, timestamp))
        }
        return tables
    }
}