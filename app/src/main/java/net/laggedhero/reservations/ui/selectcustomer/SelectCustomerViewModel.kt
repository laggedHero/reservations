package net.laggedhero.reservations.ui.selectcustomer

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import net.laggedhero.reservations.scheduler.Schedulers
import net.laggedhero.reservations.usecase.CustomerList
import net.laggedhero.reservations.usecase.CustomerList.CustomerListState

class SelectCustomerViewModel(customerList: CustomerList, schedulers: Schedulers) : ViewModel() {

    val customerListState = ObservableField<CustomerListState>()

    private var customerListDisposable: Disposable? = null

    init {
        customerListDisposable = customerList.fetch()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(
                { customerListState.set(it) },
                { _ -> }
            )
    }

    override fun onCleared() {
        customerListDisposable?.dispose()
        super.onCleared()
    }
}
