package net.laggedhero.reservations.time

interface TimeProvider {
    fun currentTimeMillis(): Long
}
