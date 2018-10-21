package net.laggedhero.reservations.ui.selecttable

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import net.laggedhero.reservations.data.Consumable
import net.laggedhero.reservations.data.table.Table
import net.laggedhero.reservations.navigator.Navigator
import net.laggedhero.reservations.scheduler.Schedulers
import net.laggedhero.reservations.usecase.TableReservation
import net.laggedhero.reservations.usecase.TableReservation.TableReservationState

class SelectTableViewModel(
    private val tableReservation: TableReservation,
    private val schedulers: Schedulers
) : ViewModel() {

    val tableReservationState = ObservableField<TableReservationState>()

    var navigator: Navigator? = null

    private val compositeDisposable = CompositeDisposable()

    init {
        val disposable = tableReservation.fetch()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(
                { tableReservationState.set(it) },
                { _ -> }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun onReserve(table: Table) {
        val disposable = tableReservation.reserve(table)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(
                { setTableReservationSuccess() },
                { _ -> setTableReservationError() }
            )
        compositeDisposable.add(disposable)
    }

    private fun setTableReservationSuccess() {
        navigator?.close("Table successfully taken")
    }

    private fun setTableReservationError() {
        val state = tableReservationState.get()
        tableReservationState.set(
            state?.copy(error = Consumable("Table already taken"))
        )
    }
}
