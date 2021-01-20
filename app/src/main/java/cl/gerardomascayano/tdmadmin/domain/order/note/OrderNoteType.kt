package cl.gerardomascayano.tdmadmin.domain.order.note


enum class OrderNoteType {
    PRIVADA, CLIENTE;

    companion object {
        fun from(isCliente: Boolean) = if (isCliente) CLIENTE else PRIVADA
    }
}