package cl.gerardomascayano.tdmadmin.domain.order

import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.data.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrdersUseCaseImpl @Inject constructor(private val repo: OrdersRepository) : OrdersUseCase {

    override fun getOrders(): Flow<PagingData<Order>> {
        return repo.getOrders()
    }
}