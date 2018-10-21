package net.laggedhero.reservations.usecase

import io.reactivex.Completable
import net.laggedhero.reservations.data.table.TableRepository
import net.laggedhero.reservations.time.TimeProvider

class TableCleanUp(
    private val localTableRepository: TableRepository,
    private val timeProvider: TimeProvider
) {

    fun cleanUp(): Completable {
        return localTableRepository.getAll()
            .map { tables -> tables.filter { !it.isAvailable } }
            .map { tables ->
                tables.map {
                    it.copy(isAvailable = true, timestamp = timeProvider.currentTimeMillis())
                }
            }
            .flatMapCompletable { localTableRepository.saveAll(it) }
    }
}
