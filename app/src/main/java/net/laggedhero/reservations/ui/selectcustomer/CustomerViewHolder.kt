package net.laggedhero.reservations.ui.selectcustomer

import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.reservations.CustomerListItemBinding
import net.laggedhero.reservations.data.customer.Customer

class CustomerViewHolder(
    private val binding: CustomerListItemBinding,
    onCustomerClicked: CustomerAdapter.OnCustomerClicked?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { _ ->
            binding.viewModel?.let { onCustomerClicked?.onCustomerClicked(it) }
        }
    }

    fun bind(customer: Customer) {
        binding.viewModel = customer
        binding.executePendingBindings()
    }
}
