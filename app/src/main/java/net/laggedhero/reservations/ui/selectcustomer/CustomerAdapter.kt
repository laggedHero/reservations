package net.laggedhero.reservations.ui.selectcustomer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.reservations.CustomerListItemBinding
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.ui.common.data.Updatable

class CustomerAdapter : RecyclerView.Adapter<CustomerViewHolder>(), Updatable<List<Customer>> {

    private val customers = mutableListOf<Customer>()

    var onCustomerClicked: OnCustomerClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = CustomerListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CustomerViewHolder(binding, onCustomerClicked)
    }

    override fun getItemCount() = customers.size

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(customers[position])
    }

    override fun update(data: List<Customer>?) {
        customers.clear()
        if (data != null) {
            customers.addAll(data)
        }
        notifyDataSetChanged()
    }

    interface OnCustomerClicked {
        fun onCustomerClicked(customer: Customer)
    }
}
