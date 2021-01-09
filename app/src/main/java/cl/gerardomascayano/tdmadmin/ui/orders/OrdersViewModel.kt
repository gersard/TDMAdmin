package cl.gerardomascayano.tdmadmin.ui.orders

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class OrdersViewModel @ViewModelInject constructor(private val useCase: OrdersUseCase) : ViewModel() {

    fun fetchOrders(): Flow<PagingData<Order>> {
        return useCase.getOrders()
            .cachedIn(viewModelScope)
    }

}