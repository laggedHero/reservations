package net.laggedhero.reservations.persistence.repository

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.data.customer.CustomerRepository
import net.laggedhero.reservations.persistence.dao.CustomerDao
import net.laggedhero.reservations.persistence.data.toEntity

class PersistenceCustomerRepository(
    private val customerDao: CustomerDao
) : CustomerRepository {

    override fun getAll(): Single<List<Customer>> {
        return customerDao.getAll()
            .map { customers -> customers.map { it.toCustomer() } }
    }

    override fun saveAll(customers: List<Customer>): Completable {
        return Completable.fromCallable {
            customerDao.saveAll(*customers.map { it.toEntity() }.toTypedArray())
        }
    }

}
