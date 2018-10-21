package net.laggedhero.reservations.network.data

import net.laggedhero.reservations.data.customer.Customer

data class ApiCustomer(
    val id: Int,
    val customerFirstName: String,
    val customerLastName: String
) {
    fun toCustomer() = Customer(id, customerFirstName, customerLastName)
}
