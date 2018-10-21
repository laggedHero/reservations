package net.laggedhero.reservations.ui.selectcustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import net.laggedhero.reservations.SelectCustomerBinding
import net.laggedhero.reservations.data.customer.Customer
import net.laggedhero.reservations.navigator.Navigator
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCustomerFragment : Fragment() {

    val viewModel: SelectCustomerViewModel by viewModel()

    var navigator: Navigator? = null

    private lateinit var binding: SelectCustomerBinding

    private val listener = object : CustomerAdapter.OnCustomerClicked {
        override fun onCustomerClicked(customer: Customer) {
            navigator?.showSelectTableScreen(customer)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = SelectCustomerBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.customerList.layoutManager = LinearLayoutManager(context)
        binding.customerList.adapter = CustomerAdapter().apply {
            onCustomerClicked = listener
        }
        binding.customerList.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}
