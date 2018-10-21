package net.laggedhero.reservations.data.customer

data class Customer(val id: Int, val firstName: String, val lastName: String) {
    companion object {
        val UNKNOWN = Customer(-1, "Unknown", "Unknown")
    }
}
