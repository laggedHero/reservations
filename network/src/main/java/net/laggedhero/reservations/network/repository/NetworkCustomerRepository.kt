package net.laggedhero.reservations.network.repository

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.data.customer.CustomerRepository
import net.laggedhero.reservations.network.api.ReservationApi

class NetworkCustomerRepository(private val reservationApi: ReservationApi) : CustomerRepository {

    override fun getAll(): Single<List<Customer>> {
        return reservationApi.getCustomers()
            .map { apiCustomers -> apiCustomers.map { it.toCustomer() } }
    }

    override fun saveAll(customers: List<Customer>): Completable {
        return Completable.complete()
    }
}