package net.laggedhero.reservations.persistence.repository

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.data.table.TableRepository
import net.laggedhero.reservations.persistence.dao.TableDao
import net.laggedhero.reservations.persistence.data.toEntity

class PersistenceTableRepository(
    private val tableDao: TableDao
) : TableRepository {

    override fun getAll(): Single<List<Table>> {
        return tableDao.getAll()
            .map { tables -> tables.map { it.toTable() } }
    }

    override fun saveAll(tables: List<Table>): Completable {
        return Completable.fromCallable {
            tableDao.saveAll(*tables.map { it.toEntity() }.toTypedArray())
        }
    }
}
