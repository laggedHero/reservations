package net.laggedhero.reservations.network.injection

import net.laggedhero.reservations.data.customer.CustomerRepository
import net.laggedhero.reservations.data.table.TableRepository
import net.laggedhero.reservations.network.api.ReservationApi
import net.laggedhero.reservations.network.repository.NetworkCustomerRepository
import net.laggedhero.reservations.network.repository.NetworkTableRepository
import net.laggedhero.reservations.time.TimeProvider
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun networkModule(customerRepositoryName: String, tableRepositoryName: String, timeProvider: TimeProvider) = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://s3-eu-west-1.amazonaws.com/quandoo-assessment/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ReservationApi::class.java)
    }
    single<CustomerRepository>(name = customerRepositoryName) { NetworkCustomerRepository(get()) }
    single<TableRepository>(name = tableRepositoryName) { NetworkTableRepository(get(), timeProvider) }
}