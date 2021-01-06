package cl.gerardomascayano.tdmadmin.data.repository

import cl.gerardomascayano.tdmadmin.data.remote.OrdersDataSource
import cl.gerardomascayano.tdmadmin.domain.order.Order
import timber.log.Timber
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrdersDataSource,
    private val orderWrapper: OrderWrapper
) : OrdersRepository {

    override suspend fun getOrders(): List<Order>? {
        remoteDataSource.getOrders()?.let { response ->
            response.body()?.let {
                Timber.d("$it")
                return orderWrapper.ordersResponseToOrder(it)
            } ?: kotlin.run {
                response.errorBody()?.let {
                    Timber.e("$${it.string()}")
                }
            }

        } ?: kotlin.run {
            Timber.e("RESPONSE NULL")
        }

        return null
    }
}