package net.laggedhero.reservations.injection

import android.app.Application
import net.laggedhero.reservations.network.injection.networkModule
import net.laggedhero.reservations.persistence.injection.persistenceModule
import net.laggedhero.reservations.scheduler.AppSchedulers
import net.laggedhero.reservations.scheduler.Schedulers
import net.laggedhero.reservations.time.AndroidTimeProvider
import net.laggedhero.reservations.time.TimeProvider
import net.laggedhero.reservations.ui.selectcustomer.SelectCustomerViewModel
import net.laggedhero.reservations.ui.selecttable.SelectTableViewModel
import net.laggedhero.reservations.usecase.CustomerList
import net.laggedhero.reservations.usecase.TableCleanUp
import net.laggedhero.reservations.usecase.TableReservation
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

internal const val REMOTE_CUSTOMER_REPOSITORY = "RemoteCustomerRepository"
internal const val LOCAL_CUSTOMER_REPOSITORY = "LocalCustomerRepository"
internal const val REMOTE_TABLE_REPOSITORY = "RemoteTableRepository"
internal const val LOCAL_TABLE_REPOSITORY = "LocalTableRepository"

internal val timeProvider = AndroidTimeProvider()

internal val network =
    networkModule(REMOTE_CUSTOMER_REPOSITORY, REMOTE_TABLE_REPOSITORY, timeProvider)

internal val persistence = persistenceModule(LOCAL_CUSTOMER_REPOSITORY, LOCAL_TABLE_REPOSITORY)

internal val appModule = module {
    single<Schedulers> { AppSchedulers() }
    single<TimeProvider> { timeProvider }
    single {
        CustomerList(
            get(name = LOCAL_CUSTOMER_REPOSITORY),
            get(name = REMOTE_CUSTOMER_REPOSITORY)
        )
    }
    single {
        TableReservation(
            get(name = LOCAL_TABLE_REPOSITORY),
            get(name = REMOTE_TABLE_REPOSITORY),
            get()
        )
    }
    single {
        TableCleanUp(
            get(name = LOCAL_TABLE_REPOSITORY),
            get()
        )
    }
    viewModel { SelectCustomerViewModel(get(), get()) }
    viewModel { SelectTableViewModel(get(), get()) }
}

fun Application.insertKoin() {
    startKoin(this, listOf(network, persistence, appModule))
}
