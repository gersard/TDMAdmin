package cl.gerardomascayano.tdmadmin.data.remote.order

import androidx.paging.PagingSource
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteCreate
import cl.gerardomascayano.tdmadmin.data.remote.order.note.OrderNoteResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class OrdersDataSource @Inject constructor(
    private val ordersService: OrdersService
) : PagingSource<Int, OrderResponse>() {

    //TODO TEMPORAL !!!!!!
    var currentFilterText = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderResponse> {
        val page = params.key ?: 1
        return try {
            val response = ordersService.getOrders(page, currentFilterText)
            LoadResult.Page(
                response.body()!!,
                if (page == 1) null else page - 1,
                if (response.body()!!.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: UnknownHostException) {
            LoadResult.Error(exception)
        }
    }

    suspend fun getOrders(page: Int, filterText: String = ""): Response<List<OrderResponse>>? {
        return try {
            ordersService.getOrders(page, filterText)
        } catch (exception: IOException) {
            null
        } catch (exception: HttpException) {
            null
        } catch (exception: UnknownHostException) {
            null
        }
    }

    suspend fun updateOrder(orderUpdate: OrderUpdate): OrderResponse? {
        return try {
            ordersService.updateOrder(orderUpdate.idOrder, orderUpdate)
        } catch (exception: IOException) {
            null
        } catch (exception: HttpException) {
            null
        } catch (exception: UnknownHostException) {
            null
        }
    }

    suspend fun getOrderNotes(orderId: Int): Response<List<OrderNoteResponse>>? {
        return try {
            ordersService.getOrderNotes(orderId)
        } catch (exception: IOException) {
            null
        } catch (exception: HttpException) {
            null
        } catch (exception: UnknownHostException) {
            null
        }
    }

    suspend fun createOrderNote(orderId: Int, orderNote: OrderNoteCreate): Response<OrderNoteResponse>? {
        return try {
            ordersService.createOrderNote(orderId, orderNote)
        } catch (exception: IOException) {
            null
        } catch (exception: HttpException) {
            null
        } catch (exception: UnknownHostException) {
            null
        }
    }
}