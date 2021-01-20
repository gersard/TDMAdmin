package cl.gerardomascayano.tdmadmin.ui.orders.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNote
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderNotesViewModel @ViewModelInject constructor(private val useCase: OrdersUseCase) : ViewModel() {

    var orderId: Int = 0

    private var _viewState = MutableStateFlow(OrderNoteViewState.LIST)
    val viewState: StateFlow<OrderNoteViewState>
        get() = _viewState

    private var _orderNoteState = MutableStateFlow<OrderNoteState>(OrderNoteState.Loading(true))
    val orderNoteState: StateFlow<OrderNoteState>
        get() = _orderNoteState

    fun toggleViewState() {
        _viewState.value = if (_viewState.value == OrderNoteViewState.LIST) OrderNoteViewState.ADD else OrderNoteViewState.LIST
    }

    fun shouldSendNote(): Boolean {
        return _viewState.value == OrderNoteViewState.ADD
    }

    fun shouldClose(): Boolean {
        return _viewState.value == OrderNoteViewState.LIST
    }

    fun listOrderNotes() {
        viewModelScope.launch {
            _orderNoteState.value = OrderNoteState.Loading(true)
            _orderNoteState.value = useCase.getOrderNotes(orderId)
            _orderNoteState.value = OrderNoteState.Loading(false)
        }
    }
}