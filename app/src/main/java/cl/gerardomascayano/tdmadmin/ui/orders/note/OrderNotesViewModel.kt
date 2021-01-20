package cl.gerardomascayano.tdmadmin.ui.orders.note

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderNotesViewModel : ViewModel() {

    private var _viewState = MutableStateFlow(OrderNoteViewState.LIST)
    val viewState: StateFlow<OrderNoteViewState>
        get() = _viewState


    fun toggleViewState() {
        _viewState.value = if (_viewState.value == OrderNoteViewState.LIST) OrderNoteViewState.ADD else OrderNoteViewState.LIST
    }


    fun shouldSendNote(): Boolean {
        return _viewState.value == OrderNoteViewState.ADD
    }

    fun shouldClose(): Boolean {
        return _viewState.value == OrderNoteViewState.LIST
    }
}