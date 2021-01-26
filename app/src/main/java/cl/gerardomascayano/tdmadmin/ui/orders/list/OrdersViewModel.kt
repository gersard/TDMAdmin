package cl.gerardomascayano.tdmadmin.ui.orders.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import cl.gerardomascayano.tdmadmin.domain.order.list.OrdersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val useCase: OrdersUseCase) : ViewModel() {

    var orders: Flow<PagingData<Order>>? = null
    var currentFilterText: String = ""
    var currentPage = 0

    fun fetchOrders(): Flow<PagingData<Order>> {
        if (orders == null) {
            orders = useCase.getOrders(currentFilterText)
                .cachedIn(viewModelScope)
        }
        return orders!!
    }

    fun fetchOrderss() = flow<OrdersViewState> {
        currentPage++
        useCase.getOrders(currentPage, currentFilterText)
    }

    fun invalidateData() {
        orders = null
    }

    fun clearOrders() {
        orders = null
        Timber.d("ON CLEARED")
    }

}