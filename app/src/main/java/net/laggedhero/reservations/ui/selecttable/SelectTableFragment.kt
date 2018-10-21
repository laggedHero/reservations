package net.laggedhero.reservations.ui.selecttable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import net.laggedhero.reservations.R
import net.laggedhero.reservations.SelectTableBinding
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.navigator.Navigator
import net.laggedhero.reservations.ui.common.GridDividerItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectTableFragment : Fragment() {

    val viewModel: SelectTableViewModel by viewModel()

    var navigator: Navigator? = null

    private lateinit var binding: SelectTableBinding

    private val listener = object : TableAdapter.OnTableClicked {
        override fun onTableClicked(table: Table) {
            viewModel.onReserve(table)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectTableBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.tableList.layoutManager = GridLayoutManager(context, 3)
        binding.tableList.adapter = TableAdapter().apply {
            onTableClicked = listener
        }

        ContextCompat.getDrawable(binding.root.context, R.drawable.grid_divider)?.let {
            binding.tableList.addItemDecoration(GridDividerItemDecoration(it, it, 3))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigator = navigator
        binding.viewModel = viewModel
    }
}
