package cl.gerardomascayano.tdmadmin.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.domain.order.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    fun getOrders(): Flow<PagingData<Order>>
}