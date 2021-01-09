package cl.gerardomascayano.tdmadmin.domain.order

import androidx.annotation.StringRes

sealed class OrderState {
    data class Loading(val isLoading: Boolean) : OrderState()
    data class Success(val data: List<Order>) : OrderState()
    data class Failure(@StringRes val messageRes: Int) : OrderState()
    object Empty : OrderState()
}
