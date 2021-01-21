package cl.gerardomascayano.tdmadmin.data.remote.order.note

import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNote
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNoteType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderNoteMapper {
    fun orderNotesResponseToOrderNotesDomain(notesResponse: List<OrderNoteResponse>) = notesResponse.map { orderNoteResponseToOrderNoteDomain(it) }

    fun orderNoteResponseToOrderNoteDomain(orderNoteResponse: OrderNoteResponse) = OrderNote(
        id = orderNoteResponse.id,
        dateCreated = LocalDateTime.parse(orderNoteResponse.dateCreated, DateTimeFormatter.ISO_DATE_TIME),
        note = orderNoteResponse.note,
        noteType = OrderNoteType.from(orderNoteResponse.customerNote)
    )

}
