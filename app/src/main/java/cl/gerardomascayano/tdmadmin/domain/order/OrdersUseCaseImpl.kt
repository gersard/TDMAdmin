package cl.gerardomascayano.tdmadmin.domain.order

import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.data.repository.OrdersRepository
import javax.inject.Inject

class OrdersUseCaseImpl @Inject constructor(private val repo: OrdersRepository) : OrdersUseCase {

    override suspend fun getOrders(): OrdersState {
        repo.getOrders()?.let {
            if (it.isNotEmpty()) {
                return OrdersState.Success(it)
            } else {
                return OrdersState.Empty()
            }
        } ?: kotlin.run {
            return OrdersState.Failure(R.string.app_name)
        }
    }
}