package cl.gerardomascayano.tdmadmin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import cl.gerardomascayano.tdmadmin.core.GenericResource
import cl.gerardomascayano.tdmadmin.data.remote.OrderUpdate
import cl.gerardomascayano.tdmadmin.data.remote.OrderWrapper
import cl.gerardomascayano.tdmadmin.data.remote.OrdersDataSource
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.network.ApiConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrdersDataSource,
    private val orderWrapper: OrderWrapper
) : OrdersRepository {


    override fun getOrders(): Flow<PagingData<Order>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { remoteDataSource }
        ).flow.map { pagingDataNetwork ->
            pagingDataNetwork.map { orderResponse -> orderWrapper.orderResponseToOrder(orderResponse) }
        }
    }

    override suspend fun updateOrder(orderId: Int, status: String): GenericResource<*> {
        return remoteDataSource.updateOrder(OrderUpdate(orderId, status))?.let {
            GenericResource.Success(it)
        } ?: kotlin.run { GenericResource.Failure("Error al actualizar orden") }
    }


    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = ApiConstants.DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }
}