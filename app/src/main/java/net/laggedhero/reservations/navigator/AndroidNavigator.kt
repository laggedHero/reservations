package net.laggedhero.reservations.navigator

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.laggedhero.reservations.R
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.ui.selecttable.SelectTableFragment

class AndroidNavigator(private val activity: AppCompatActivity) : Navigator {

    override fun showSelectTableScreen(customer: Customer) {
        val fragment = SelectTableFragment()
        fragment.navigator = this
        activity.supportFragmentManager.beginTransaction()
            .add(R.id.content_area, fragment)
            .addToBackStack("SelectTableFragment")
            .commit()
    }

    override fun close(message: String?) {
        message?.let { Toast.makeText(activity, it, Toast.LENGTH_LONG).show() }
        activity.supportFragmentManager.popBackStack()
    }
}
