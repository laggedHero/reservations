package net.laggedhero.reservations.usecase

import io.reactivex.Flowable
import io.reactivex.Single
import net.laggedhero.reservations.data.Consumable
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.data.customer.CustomerRepository

class CustomerList(
    private val localCustomerRepository: CustomerRepository,
    private val remoteCustomerRepository: CustomerRepository
) {

    fun fetch(): Flowable<CustomerListState> {
        return fetchAndCache()
            .map { CustomerListState(it, false) }
            .onErrorResumeNext { _ ->
                localCustomerRepository.getAll()
                    .map {
                        CustomerListState(
                            it,
                            false,
                            Consumable("Error fetching customers")
                        )
                    }
            }
            .toFlowable()
            .startWith(CustomerListState())
    }

    private fun fetchAndCache(): Single<List<Customer>> {
        return remoteCustomerRepository.getAll()
            .flatMap { localCustomerRepository.saveAll(it).andThen(Single.just(it)) }
    }

    data class CustomerListState(
        val customers: List<Customer> = listOf(),
        val isLoading: Boolean = true,
        val error: Consumable<String>? = null
    )
}
