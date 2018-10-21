package net.laggedhero.reservations.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import net.laggedhero.reservations.persistence.dao.CustomerDao
import net.laggedhero.reservations.persistence.dao.TableDao
import net.laggedhero.reservations.persistence.data.EntityCustomer
import net.laggedhero.reservations.persistence.data.EntityTable

@Database(
    entities = [EntityCustomer::class, EntityTable::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCustomerDao(): CustomerDao
    abstract fun getTableDao(): TableDao
}
