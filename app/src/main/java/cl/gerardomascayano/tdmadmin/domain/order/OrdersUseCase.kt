package cl.gerardomascayano.tdmadmin.domain.order

import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.ui.orders.OrderState
import kotlinx.coroutines.flow.Flow

interface OrdersUseCase {

    fun getOrders() : Flow<PagingData<Order>>
}