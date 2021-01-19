package cl.gerardomascayano.tdmadmin.domain.order.detail

import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import cl.gerardomascayano.tdmadmin.ui.orders.detail.TypeContent
import java.time.LocalDateTime

data class OrderDateState(
    val dateCreated: LocalDateTime,
    var state: OrderState,
    var isUpdating: Boolean,
    val typeContent: TypeContent = TypeContent.NO_TYPE,
    val useClickListener: Boolean = false
)
