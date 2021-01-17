package cl.gerardomascayano.tdmadmin.data.repository

import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.core.GenericState
import cl.gerardomascayano.tdmadmin.domain.order.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    fun getOrders(): Flow<PagingData<Order>>
    suspend fun updateOrder(orderId: Int, status: String): GenericState
}
