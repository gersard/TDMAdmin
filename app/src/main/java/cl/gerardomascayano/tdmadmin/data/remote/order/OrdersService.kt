package cl.gerardomascayano.tdmadmin.data.remote.order

import cl.gerardomascayano.tdmadmin.data.remote.network.ApiConstants
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteCreate
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteResponse
import retrofit2.Response
import retrofit2.http.*

interface OrdersService {


    @GET(ApiConstants.METHOD_ORDERS)
    suspend fun getOrders(@Query(ApiConstants.PARAM_PAGE) page: Int, @Query(ApiConstants.PARAM_SEARCH) filterText: String): Response<List<OrderResponse>>

    @PUT("${ApiConstants.METHOD_ORDERS}/{id}")
    suspend fun updateOrder(@Path("id") id: Int, @Body orderUpdate: OrderUpdate): OrderResponse?

    @GET("${ApiConstants.METHOD_ORDERS}/{id}/${ApiConstants.METHOD_NOTES}")
    suspend fun getOrderNotes(@Path("id") id: Int): Response<List<OrderNoteResponse>>?

    @POST("${ApiConstants.METHOD_ORDERS}/{id}/${ApiConstants.METHOD_NOTES}")
    suspend fun createOrderNote(@Path("id") id: Int, @Body orderNoteCreate: OrderNoteCreate): Response<OrderNoteResponse>?

}