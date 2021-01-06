package cl.gerardomascayano.tdmadmin.domain.order

import cl.gerardomascayano.tdmadmin.domain.order.OrdersState

interface OrdersUseCase {

    suspend fun getOrders() : OrdersState<*>
}