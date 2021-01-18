package cl.gerardomascayano.tdmadmin.domain.order

import androidx.annotation.ColorRes
import cl.gerardomascayano.tdmadmin.R

enum class OrderState(
    val id: String,
    val description: String,
    @ColorRes val color: Int
) {
    PENDING("pending", "Pendiente de pago", R.color.order_status_pending),
    PROCESSING("processing", "Procesando", R.color.order_status_processing),
    COMPLETED("completed", "Completado", R.color.order_status_completed),
    ON_HOLD("on-hold", "En espera", R.color.order_status_onhold),
    CANCELLED("cancelled", "Cancelado", R.color.order_status_canceled),
    REFUNDED("refunded", "Reembolsado", R.color.order_status_refunded),
    FAILED("failed", "Fallido", R.color.order_status_failed),
    DELETED("trash", "Eliminado", R.color.order_status_deleted);

    companion object {
        fun from(id: String) = values().first { it.id == id }
        fun fromDescription(description: String) = values().first { it.description == description }
    }
}