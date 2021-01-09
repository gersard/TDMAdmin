package cl.gerardomascayano.tdmadmin.ui.orders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrdersViewModel @ViewModelInject constructor(private val useCase: OrdersUseCase) : ViewModel() {

    private val _ordersState = MutableStateFlow<OrderState>(OrderState.Empty)
    val orderState: StateFlow<OrderState>
        get() = _ordersState


    fun getOrders() {
        viewModelScope.launch {
            _ordersState.value = OrderState.Loading(true)
            val resultState = useCase.getOrders()
            _ordersState.value = OrderState.Loading(false)
            _ordersState.value = resultState
        }
    }
}