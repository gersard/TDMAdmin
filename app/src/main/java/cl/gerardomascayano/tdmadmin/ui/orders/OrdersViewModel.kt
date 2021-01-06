package cl.gerardomascayano.tdmadmin.ui.orders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import kotlinx.coroutines.launch

class OrdersViewModel @ViewModelInject constructor(private val useCase: OrdersUseCase) : ViewModel() {




    fun getOrders(){
        viewModelScope.launch {
            val state = useCase.getOrders()
        }
    }
}