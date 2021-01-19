package cl.gerardomascayano.tdmadmin.domain.order.detail

import cl.gerardomascayano.tdmadmin.ui.orders.detail.TypeContent

data class OrderContentTextDetail(
    val key: String,
    val value: String,
    val typeContent: TypeContent = TypeContent.NO_TYPE,
    val useClickListener: Boolean = false
)
