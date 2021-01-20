package cl.gerardomascayano.tdmadmin.data.remote.order

/**
 * Por ahora solo se actualizará status
 */
data class OrderUpdate(val idOrder: Int,val status: String)
