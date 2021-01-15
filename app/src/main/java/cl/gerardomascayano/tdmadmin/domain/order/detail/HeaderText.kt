package cl.gerardomascayano.tdmadmin.domain.order.detail

data class HeaderText(val title: String, val campos: List<HeaderTextDetail>) : Header(title)
