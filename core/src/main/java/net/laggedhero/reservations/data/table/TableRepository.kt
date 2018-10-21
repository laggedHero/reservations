package net.laggedhero.reservations.data.table

import io.reactivex.Completable
import io.reactivex.Single

interface TableRepository {
    fun getAll(): Single<List<Table>>
    fun saveAll(tables: List<Table>): Completable
}
