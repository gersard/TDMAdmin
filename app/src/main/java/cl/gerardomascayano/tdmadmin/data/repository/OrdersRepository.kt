package cl.gerardomascayano.tdmadmin.data.repository

import cl.gerardomascayano.tdmadmin.domain.order.Order

interface OrdersRepository {

    suspend fun getOrders(): List<Order>?
}