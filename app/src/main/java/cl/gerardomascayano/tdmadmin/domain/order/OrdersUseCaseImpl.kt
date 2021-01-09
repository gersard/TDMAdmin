package cl.gerardomascayano.tdmadmin.domain.order

import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.data.repository.OrdersRepository
import javax.inject.Inject

class OrdersUseCaseImpl @Inject constructor(private val repo: OrdersRepository) : OrdersUseCase {

    override suspend fun getOrders(): OrderState {
        repo.getOrders()?.let {
            if (it.isNotEmpty()) {
                return OrderState.Success(it)
            } else {
                return OrderState.Empty
            }
        } ?: kotlin.run {
            return OrderState.Failure(R.string.app_name)
        }
    }
}