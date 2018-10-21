package net.laggedhero.reservations.network.repository

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.data.table.TableRepository
import net.laggedhero.reservations.network.api.ReservationApi
import net.laggedhero.reservations.time.TimeProvider

class NetworkTableRepository(
    private val reservationApi: ReservationApi,
    private val timeProvider: TimeProvider
) : TableRepository {

    override fun getAll(): Single<List<Table>> {
        val timestamp = timeProvider.currentTimeMillis()
        return reservationApi.getTables()
            .map { apiTables ->
                apiTables.mapIndexed { index, isAvailable ->
                    Table(index, isAvailable, timestamp)
                }
            }
    }

    override fun saveAll(tables: List<Table>): Completable {
        return Completable.complete()
    }
}
