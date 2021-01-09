package cl.gerardomascayano.tdmadmin.ui.orders

import androidx.annotation.StringRes
import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.domain.order.Order

sealed class OrderState {
    data class Loading(val isLoading: Boolean) : OrderState()
    data class Success(val data: PagingData<Order>) : OrderState()
    data class Failure(@StringRes val messageRes: Int) : OrderState()
    object Empty : OrderState()
}
