package cl.gerardomascayano.tdmadmin.domain.order.detail

import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import java.time.LocalDateTime

data class OrderDateState(val dateCreated: LocalDateTime,val state: OrderState)