package cl.gerardomascayano.tdmadmin.domain.order

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface OrdersUseCase {

    fun getOrders() : Flow<PagingData<Order>>
}