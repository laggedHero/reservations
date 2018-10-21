package net.laggedhero.reservations.usecase

import io.reactivex.Completable
import io.reactivex.Single
import net.laggedhero.reservations.any
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.data.customer.CustomerRepository
import net.laggedhero.reservations.usecase.CustomerList.CustomerListState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CustomerListTest {

    @Mock
    lateinit var localCustomerRepository: CustomerRepository
    @Mock
    lateinit var remoteCustomerRepository: CustomerRepository

    lateinit var customerList: CustomerList

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        customerList = CustomerList(localCustomerRepository, remoteCustomerRepository)
    }

    @Test
    fun `should return a initial loading state`() {
        `when`(remoteCustomerRepository.getAll())
            .thenReturn(Single.never())

        customerList.fetch()
            .test()
            .assertNotTerminated()
            .assertValue(CustomerListState())
    }

    @Test
    fun `should fetch from the remote and cache`() {
        val customers = getCustomers(2)

        `when`(localCustomerRepository.saveAll(any()))
            .thenReturn(Completable.complete())
        `when`(remoteCustomerRepository.getAll())
            .thenReturn(Single.just(customers))

        customerList.fetch()
            .test()
            .assertValues(
                CustomerListState(),
                CustomerListState(customers, false)
            )

        verify(localCustomerRepository, times(1))
            .saveAll(customers)
        verify(remoteCustomerRepository, times(1))
            .getAll()
    }

    @Test
    fun `should have an error message and use local cache when fail to fetch from remote`() {
        val customers = getCustomers(2)

        `when`(localCustomerRepository.getAll())
            .thenReturn(Single.just(listOf()))
        `when`(remoteCustomerRepository.getAll())
            .thenReturn(Single.error(Throwable()))

        val values = customerList.fetch()
            .test()
            .values()

        assertEquals(CustomerListState(), values[0])
        assertTrue(values[1].customers.isEmpty())
        assertFalse(values[1].isLoading)
        assertNotNull(values[1].error)

        verify(localCustomerRepository, times(1))
            .getAll()
        verify(localCustomerRepository, times(0))
            .saveAll(customers)
        verify(remoteCustomerRepository, times(1))
            .getAll()
    }

    private fun getCustomers(quantity: Int): List<Customer> {
        val customers = mutableListOf<Customer>()
        for (i in 0 until quantity) {
            customers.add(getCustomer(i))
        }
        return customers
    }

    private fun getCustomer(value: Int) = Customer(
        value, "firstName-$value", "lastName-$value"
    )
}