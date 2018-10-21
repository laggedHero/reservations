package net.laggedhero.reservations.data

class Consumable<T>(private val data: T) {
    private var consumed = false

    operator fun invoke(): T? {
        if (consumed) return null
        consumed = true
        return data
    }
}
