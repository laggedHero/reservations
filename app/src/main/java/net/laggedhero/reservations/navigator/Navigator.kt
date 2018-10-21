package net.laggedhero.reservations.navigator

import net.laggedhero.reservations.data.customer.Customer

interface Navigator {
    fun showSelectTableScreen(customer: Customer)
    fun close(message: String? = null)
}
