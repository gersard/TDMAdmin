package cl.gerardomascayano.tdmadmin.data.remote

import androidx.paging.PagingSource
import cl.gerardomascayano.tdmadmin.domain.order.Order
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class OrdersDataSource @Inject constructor(
    private val ordersService: OrdersService
) : PagingSource<Int, OrderResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderResponse> {
        val page = params.key ?: 1
        val response = ordersService.getOrders(page)
        return try {
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
}