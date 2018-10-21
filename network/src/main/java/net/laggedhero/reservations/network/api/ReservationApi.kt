package net.laggedhero.reservations.network.api

import io.reactivex.Single
import net.laggedhero.reservations.network.data.ApiCustomer
import retrofit2.http.GET

interface ReservationApi {

    @GET("customer-list.json")
    fun getCustomers(): Single<List<ApiCustomer>>

    @GET("table-map.json")
    fun getTables(): Single<List<Boolean>>
}
