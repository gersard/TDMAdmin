package cl.gerardomascayano.tdmadmin.domain.order.note


enum class OrderNoteType(val description: String) {
    PRIVADA("Nota privada"), CLIENTE("Nota cliente");

    companion object {
        fun from(isCliente: Boolean) = if (isCliente) CLIENTE else PRIVADA
    }
}