package cl.gerardomascayano.tdmadmin.domain.order

enum class OrderStatus(
    private val id: String,
    private val description: String,
    private val color: String
) {
    PENDING("pending", "Pendiente de pago", ""),
    PROCESSING("processing", "Procesando", ""),
    ON_HOLD("on-hold", "En espera", ""),
    CANCELLED("cancelled", "Cancelado", ""),
    REEMBOLSADO("refunded", "Reembolsado", ""),
    FALLIDO("failed", "Fallido", ""),
    ELIMINADO("trash", "Eliminado", "");

    companion object{
        fun from(id: String) = values().first { it.id == id }
    }
}