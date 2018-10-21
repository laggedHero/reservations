package net.laggedhero.reservations.persistence.injection

import androidx.room.Room
import net.laggedhero.reservations.data.customer.CustomerRepository
import net.laggedhero.reservations.data.table.TableRepository
import net.laggedhero.reservations.persistence.AppDatabase
import net.laggedhero.reservations.persistence.repository.PersistenceCustomerRepository
import net.laggedhero.reservations.persistence.repository.PersistenceTableRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

fun persistenceModule(customerRepositoryName: String, tableRepositoryName: String) = module {
    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "appDb").build() }
    single { get<AppDatabase>().getCustomerDao() }
    single { get<AppDatabase>().getTableDao() }
    single<CustomerRepository>(name = customerRepositoryName) { PersistenceCustomerRepository(get()) }
    single<TableRepository>(name = tableRepositoryName) { PersistenceTableRepository(get()) }
}
