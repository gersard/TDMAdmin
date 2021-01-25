package cl.gerardomascayano.tdmadmin.domain.order.list

import cl.gerardomascayano.tdmadmin.domain.order.Order


sealed class OrdersViewState {
    data class Loading(val isLoading: Boolean) : OrdersViewState()
    data class Error(val errorMessage: String) : OrdersViewState()
    data class Success(var data: List<Order>) : OrdersViewState()
    object EndPageReached : OrdersViewState()
    object EmptyList : OrdersViewState()

}