package net.laggedhero.reservations.persistence.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.laggedhero.reservations.data.table.Table

@Entity
data class EntityTable(
    @PrimaryKey val id: Int,
    val isAvailable: Boolean,
    val timestamp: Long
) {
    fun toTable() = Table(id, isAvailable, timestamp)
}

fun Table.toEntity() = EntityTable(id, isAvailable, timestamp)
