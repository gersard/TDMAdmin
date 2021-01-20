package cl.gerardomascayano.tdmadmin.domain.order.note

import java.time.LocalDateTime

data class OrderNote(
    val id: Int,
    val dateCreated: LocalDateTime,
    val note: String,
    val noteType: OrderNoteType
)
