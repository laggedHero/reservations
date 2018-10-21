package net.laggedhero.reservations.data.customer

import io.reactivex.Completable
import io.reactivex.Single

interface CustomerRepository {
    fun getAll(): Single<List<Customer>>
    fun saveAll(customers: List<Customer>): Completable
}
