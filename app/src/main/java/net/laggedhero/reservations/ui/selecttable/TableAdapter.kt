package net.laggedhero.reservations.ui.selecttable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.reservations.TableGridItemBinding
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.ui.common.data.Updatable

class TableAdapter : RecyclerView.Adapter<TableViewHolder>(), Updatable<List<Table>> {

    private val tables = mutableListOf<Table>()

    var onTableClicked: OnTableClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val binding = TableGridItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TableViewHolder(binding, onTableClicked)
    }

    override fun getItemCount() = tables.size

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bind(tables[position])
    }

    override fun update(data: List<Table>?) {
        tables.clear()
        if (data != null) {
            tables.addAll(data)
        }
        notifyDataSetChanged()
    }

    interface OnTableClicked {
        fun onTableClicked(table: Table)
    }
}
