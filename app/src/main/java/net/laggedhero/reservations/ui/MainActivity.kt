package net.laggedhero.reservations.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.laggedhero.reservations.R
import net.laggedhero.reservations.navigator.AndroidNavigator
import net.laggedhero.reservations.ui.selectcustomer.SelectCustomerFragment

class MainActivity : AppCompatActivity() {

    private val navigator = AndroidNavigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showCustomerList()
    }

    private fun showCustomerList() {
        val fragment = SelectCustomerFragment()
        fragment.navigator = navigator
        supportFragmentManager.beginTransaction()
            .add(R.id.content_area, fragment)
            .commit()
    }
}
