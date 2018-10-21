package net.laggedhero.reservations.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import net.laggedhero.reservations.persistence.data.EntityCustomer

@Dao
interface CustomerDao {

    @Query("SELECT * FROM EntityCustomer")
    fun getAll(): Single<List<EntityCustomer>>

    @Query("SELECT * FROM EntityCustomer WHERE id = :id")
    fun getById(id: Int): Single<EntityCustomer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(vararg entityCustomers: EntityCustomer)
}
