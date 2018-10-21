package net.laggedhero.reservations.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import net.laggedhero.reservations.persistence.data.EntityTable

@Dao
interface TableDao {

    @Query("SELECT * FROM EntityTable")
    fun getAll(): Single<List<EntityTable>>

    @Query("SELECT * FROM EntityTable WHERE id = :id")
    fun getById(id: Int): Single<EntityTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(vararg entityTables: EntityTable)
}
