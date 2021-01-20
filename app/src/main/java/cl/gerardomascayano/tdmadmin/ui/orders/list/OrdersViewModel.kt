package cl.gerardomascayano.tdmadmin.ui.orders.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class OrdersViewModel @ViewModelInject constructor(private val useCase: OrdersUseCase) : ViewModel() {

    var orders: Flow<PagingData<Order>>? = null

    fun fetchOrders(): Flow<PagingData<Order>> {
        if (orders == null) {
            orders = useCase.getOrders()
                .cachedIn(viewModelScope)
        }
        return orders!!
    }

    fun invalidateData() {
        orders = null
    }

    fun clearOrders() {
        orders = null
        Timber.d("ON CLEARED")
    }

}