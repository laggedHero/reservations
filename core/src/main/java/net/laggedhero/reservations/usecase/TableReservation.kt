package net.laggedhero.reservations.usecase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import net.laggedhero.reservations.data.Consumable
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.data.table.TableRepository
import net.laggedhero.reservations.time.TimeProvider

class TableReservation(
    private val localTableRepository: TableRepository,
    private val remoteTableRepository: TableRepository,
    private val timeProvider: TimeProvider
) {

    fun fetch(): Flowable<TableReservationState> {
        return localTableRepository.getAll()
            .flatMap {
                if (it.isEmpty()) fetchAndFillCache()
                else Single.just(it)
            }
            .map { TableReservationState(it, false) }
            .onErrorResumeNext { _ ->
                localTableRepository.getAll()
                    .map {
                        TableReservationState(
                            it,
                            false,
                            Consumable("Error fetching tables")
                        )
                    }
            }
            .toFlowable()
            .startWith(TableReservationState())
    }

    private fun fetchAndFillCache(): Single<List<Table>> {
        return remoteTableRepository.getAll()
            .flatMap { localTableRepository.saveAll(it).andThen(Single.just(it)) }
    }

    private fun fetchAndMergeCache(): Single<List<Table>> {
        return remoteTableRepository.getAll()
            .map { tables -> tables.filter { !it.isAvailable } }
            .flatMapCompletable { localTableRepository.saveAll(it) }
            .andThen(localTableRepository.getAll())
    }

    fun reserve(table: Table): Completable {
        return Single.just(table)
            .flatMapCompletable {
                if (!it.isAvailable) Completable.error(Throwable())
                else localTableRepository.saveAll(
                    listOf(
                        it.copy(isAvailable = false, timestamp = timeProvider.currentTimeMillis())
                    )
                )
            }
    }

    data class TableReservationState(
        val tables: List<Table> = listOf(),
        val isLoading: Boolean = true,
        val error: Consumable<String>? = null
    )
}
