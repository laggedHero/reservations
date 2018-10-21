package net.laggedhero.reservations.time

class AndroidTimeProvider : TimeProvider {

    override fun currentTimeMillis() = System.currentTimeMillis()
}
