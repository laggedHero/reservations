package net.laggedhero.reservations.ui.common.binding

import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter
import net.laggedhero.reservations.data.Consumable

object ViewBindings {

    @JvmStatic
    @BindingAdapter("showError")
    fun showError(view: View, error: String?) {
        if (error.isNullOrBlank()) return
        Toast.makeText(view.context, error, Toast.LENGTH_LONG).show()
    }

    @JvmStatic
    @BindingAdapter("showError")
    fun showError(view: View, consumable: Consumable<String>?) {
        if (consumable == null) return
        showError(view, consumable())
    }

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun visibleIf(view: View, isVisible: Boolean?) {
        view.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }
}
