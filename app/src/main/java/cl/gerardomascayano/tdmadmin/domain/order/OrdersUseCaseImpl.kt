package cl.gerardomascayano.tdmadmin.domain.order

import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.core.GenericState
import cl.gerardomascayano.tdmadmin.data.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrdersUseCaseImpl @Inject constructor(private val repo: OrdersRepository) : OrdersUseCase {

    override fun getOrders(): Flow<PagingData<Order>> {
        return repo.getOrders()
    }

    override suspend fun updateStatus(orderId: Int, status: String): GenericState {
        return repo.updateOrder(orderId, status)
    }


}