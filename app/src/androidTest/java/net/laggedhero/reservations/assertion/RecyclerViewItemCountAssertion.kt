package net.laggedhero.reservations.assertion

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher

class RecyclerViewItemCountAssertion(private val matcher: Matcher<Int>) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val itemCount = (view as? RecyclerView)?.adapter?.itemCount ?: -1
        assertThat(itemCount, matcher)
    }

    companion object {
        fun withItemCount(count: Int): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(`is`(count))
        }
    }
}
