package cl.gerardomascayano.tdmadmin.data.remote

import cl.gerardomascayano.tdmadmin.network.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersService {


    @GET(ApiConstants.METHOD_ORDERS)
    suspend fun getOrders(@Query(ApiConstants.PARAM_PAGE) page: Int): Response<List<OrderResponse>>

}