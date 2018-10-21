package net.laggedhero.reservations.network.repository

import io.reactivex.Single
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.network.api.ReservationApi
import net.laggedhero.reservations.network.data.ApiCustomer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NetworkCustomerRepositoryTest {

    @Mock
    lateinit var reservationApi: ReservationApi

    lateinit var repository: NetworkCustomerRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = NetworkCustomerRepository(reservationApi)
    }

    @Test
    fun `getAll returns a mapped api object to core object`() {
        `when`(reservationApi.getCustomers())
            .thenReturn(Single.just(getApiCustomers(3)))

        repository.getAll()
            .test()
            .assertComplete()
            .assertValue(getCustomers(3))
    }

    @Test
    fun `saveAll completes successfully`() {
        repository.saveAll(getCustomers(1))
            .test()
            .assertComplete()
    }

    private fun getApiCustomers(quantity: Int): List<ApiCustomer> {
        val apiCustomers = mutableListOf<ApiCustomer>()
        for (i in 0 until quantity) {
            apiCustomers.add(getApiCustomer(i))
        }
        return apiCustomers
    }

    private fun getApiCustomer(id: Int) = ApiCustomer(id, "firstName-$id", "lastName-$id")

    private fun getCustomers(quantity: Int): List<Customer> {
        val customers = mutableListOf<Customer>()
        for (i in 0 until quantity) {
            customers.add(getCustomer(i))
        }
        return customers
    }

    private fun getCustomer(id: Int) = Customer(id, "firstName-$id", "lastName-$id")
}