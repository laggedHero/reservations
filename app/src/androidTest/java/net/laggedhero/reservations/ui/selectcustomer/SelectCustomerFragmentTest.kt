package net.laggedhero.reservations.ui.selectcustomer

import androidx.databinding.ObservableField
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.laggedhero.reservations.R
import net.laggedhero.reservations.assertion.RecyclerViewItemCountAssertion.Companion.withItemCount
import net.laggedhero.reservations.data.Consumable
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.navigator.Navigator
import net.laggedhero.reservations.ui.TestFragmentActivity
import net.laggedhero.reservations.usecase.CustomerList.CustomerListState
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class SelectCustomerFragmentTest {

    val viewModel = mock(SelectCustomerViewModel::class.java)
    val navigator = mock(Navigator::class.java)

    val testRule = object :
        ActivityTestRule<TestFragmentActivity>(TestFragmentActivity::class.java, true, true) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            loadKoinModules(
                module(override = true) {
                    viewModel { viewModel }
                }
            )
        }
    }

    @Rule
    fun rule() = testRule

    @Before
    fun setUp() {
        val fragment = SelectCustomerFragment()
        fragment.navigator = navigator
        rule().activity.setFragment(fragment)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun showLoaderWhenStateIsLoading() {
        val customerListState = ObservableField(CustomerListState())
        `when`(viewModel.customerListState).thenReturn(customerListState)

        onView(withId(R.id.progress_overlay)).check(matches(isDisplayed()))
    }

    @Test
    fun doesNotShowLoaderWhenStateIsLoaded() {
        val customerListState = ObservableField(CustomerListState(isLoading = false))
        `when`(viewModel.customerListState).thenReturn(customerListState)

        onView(withId(R.id.progress_overlay)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun showErrorMessageWhenStateHaveError() {
        val customerListState = ObservableField(
            CustomerListState(isLoading = false, error = Consumable("Error Message"))
        )
        `when`(viewModel.customerListState).thenReturn(customerListState)

        onView(withText("Error Message"))
            .inRoot(withDecorView(not(`is`(testRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun listAllCustomersPresentOnState() {
        val customerListState = ObservableField(
            CustomerListState(customers = getCustomers(3), isLoading = false)
        )
        `when`(viewModel.customerListState).thenReturn(customerListState)

        onView(withId(R.id.customer_list))
            .check(withItemCount(3))
    }

    @Test
    fun callsNavigatorShowSelectTableScreenWithSelectedCustomer() {
        val customerListState = ObservableField(
            CustomerListState(customers = getCustomers(3), isLoading = false)
        )
        `when`(viewModel.customerListState).thenReturn(customerListState)

        onView(withId(R.id.customer_list))
            .perform(actionOnItemAtPosition<CustomerViewHolder>(1, click()))

        verify(navigator, times(1))
            .showSelectTableScreen(getCustomer(1))
    }

    private fun getCustomers(quantity: Int) = mutableListOf<Customer>()
        .apply {
            for (i in 0 until quantity) {
                add(getCustomer(i))
            }
        }

    private fun getCustomer(id: Int) = Customer(id, "firstName-$id", "lastName-$id")
}