package net.laggedhero.reservations.ui.selecttable

import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.reservations.TableGridItemBinding
import net.laggedhero.reservations.data.table.Table

class TableViewHolder(
    private val binding: TableGridItemBinding,
    onTableClicked: TableAdapter.OnTableClicked?
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { _ ->
            binding.viewModel?.let { onTableClicked?.onTableClicked(it) }
        }
    }

    fun bind(table: Table) {
        binding.viewModel = table
        binding.executePendingBindings()
    }
}
