package cl.gerardomascayano.tdmadmin.data.remote

import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class OrdersDataSourceImpl @Inject constructor(private val ordersService: OrdersService): OrdersDataSource {
    override suspend fun getOrders(): Response<List<OrderResponse>>? = try {
        ordersService.getOrders()
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}