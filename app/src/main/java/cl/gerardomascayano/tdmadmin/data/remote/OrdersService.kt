package cl.gerardomascayano.tdmadmin.data.remote

import cl.gerardomascayano.tdmadmin.network.ApiConstants
import retrofit2.Response
import retrofit2.http.GET

interface OrdersService {


    @GET(ApiConstants.METHOD_ORDERS)
    suspend fun getOrders(): Response<List<OrderResponse>>

}