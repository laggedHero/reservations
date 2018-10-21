package net.laggedhero.reservations.ui.common.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import net.laggedhero.reservations.ui.common.data.Updatable

object RecyclerViewBindings {

    @JvmStatic
    @BindingAdapter("setData")
    fun <T> setData(recyclerView: RecyclerView, data: T?) {
        (recyclerView.adapter as? Updatable<T>)?.update(data)
    }
}
