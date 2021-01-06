package cl.gerardomascayano.tdmadmin.data.remote

import retrofit2.Response

interface OrdersDataSource {

    suspend fun getOrders(): Response<List<OrderResponse>>?

}