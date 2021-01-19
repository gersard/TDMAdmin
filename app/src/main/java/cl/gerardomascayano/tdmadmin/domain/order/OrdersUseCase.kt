package cl.gerardomascayano.tdmadmin.domain.order

import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.core.GenericState
import kotlinx.coroutines.flow.Flow

interface OrdersUseCase {

    fun getOrders(): Flow<PagingData<Order>>
    suspend fun updateStatus(orderId: Int, status: String): GenericState
    fun invalidateData()
}