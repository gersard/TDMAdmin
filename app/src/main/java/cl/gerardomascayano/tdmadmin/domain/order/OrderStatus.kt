package cl.gerardomascayano.tdmadmin.domain.order

enum class OrderStatus(
    private val id: String,
    val description: String,
    val color: String
) {
    PENDING("pending", "Pendiente de pago", "#E65100"),
    PROCESSING("processing", "Procesando", "#0D47A1"),
    COMPLETED("completed", "Completado", "#000000"),
    ON_HOLD("on-hold", "En espera", "#4A148C"),
    CANCELLED("cancelled", "Cancelado", "#827717"),
    REEMBOLSADO("refunded", "Reembolsado", "#212121"),
    FALLIDO("failed", "Fallido", "#3E2723"),
    ELIMINADO("trash", "Eliminado", "#B71C1C");

    companion object {
        fun from(id: String) = values().first { it.id == id }
    }
}