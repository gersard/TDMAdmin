package cl.gerardomascayano.tdmadmin.domain.order

import androidx.paging.PagingData
import cl.gerardomascayano.tdmadmin.core.GenericState
import cl.gerardomascayano.tdmadmin.data.repository.OrdersRepository
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNoteState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrdersUseCaseImpl @Inject constructor(private val repo: OrdersRepository) : OrdersUseCase {

    override fun getOrders(filterText: String): Flow<PagingData<Order>> {
        return repo.getOrders(filterText)
    }

    override suspend fun updateStatus(orderId: Int, status: String): GenericState {
        return repo.updateOrder(orderId, status)
    }

    override suspend fun getOrderNotes(orderId: Int): OrderNoteState {
        return repo.getOrderNotes(orderId)
    }

    override suspend fun createOrderNote(orderId: Int, note: String, customerNote: Boolean): GenericState {
        return if (note.isNotEmpty()) {
            repo.createOrderNote(orderId, note, customerNote)
        } else {
            GenericState.Error("Debes escribir el contenido de la nota")
        }

    }


}