package cl.gerardomascayano.tdmadmin.data.remote.order

import cl.gerardomascayano.tdmadmin.data.remote.network.ApiConstants
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteResponse
import retrofit2.Response
import retrofit2.http.*

interface OrdersService {


    @GET(ApiConstants.METHOD_ORDERS)
    suspend fun getOrders(@Query(ApiConstants.PARAM_PAGE) page: Int): Response<List<OrderResponse>>

    @PUT("${ApiConstants.METHOD_ORDERS}/{id}")
    suspend fun updateOrder(@Path("id") id: Int, @Body orderUpdate: OrderUpdate): OrderResponse?

    @GET("${ApiConstants.METHOD_ORDERS}/{id}/${ApiConstants.METHOD_ORDERS}")
    suspend fun getOrderNotes(@Path("id") id: Int): Response<List<OrderNoteResponse>>?

}