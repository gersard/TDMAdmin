package cl.gerardomascayano.tdmadmin.domain.order

interface OrdersUseCase {

    suspend fun getOrders() : OrderState
}