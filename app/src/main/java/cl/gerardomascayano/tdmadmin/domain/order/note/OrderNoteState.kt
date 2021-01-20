package cl.gerardomascayano.tdmadmin.domain.order.note

sealed class OrderNoteState {
    data class Loading(val isLoading: Boolean) : OrderNoteState()
    data class Error(val errorMessage: String) : OrderNoteState()
    data class Success<OrderNote>(var data: OrderNote) : OrderNoteState()
}
