package cl.gerardomascayano.tdmadmin.domain.order

import androidx.annotation.StringRes

sealed class OrdersState {
    data class Loading(val isLoading: Boolean) : OrdersState()
    data class Success(val data: List<Order>) : OrdersState()
    data class Failure(@StringRes val messageRes: Int) : OrdersState()
    class Empty : OrdersState()
}
