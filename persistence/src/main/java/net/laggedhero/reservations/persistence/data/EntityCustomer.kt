package net.laggedhero.reservations.persistence.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.laggedhero.reservations.data.customer.Customer

@Entity
data class EntityCustomer(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String
) {
    fun toCustomer() = Customer(id, firstName, lastName)
}

fun Customer.toEntity() = EntityCustomer(id, firstName, lastName)
