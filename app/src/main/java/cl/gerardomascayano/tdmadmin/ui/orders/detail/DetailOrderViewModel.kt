package cl.gerardomascayano.tdmadmin.ui.orders.detail

import androidx.lifecycle.ViewModel
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.Product
import cl.gerardomascayano.tdmadmin.domain.order.detail.HeaderOrderText
import cl.gerardomascayano.tdmadmin.domain.order.detail.OrderContentTextDetail
import cl.gerardomascayano.tdmadmin.domain.order.detail.OrderDateState

class DetailOrderViewModel : ViewModel() {

    fun generateData(order: Order) {
        orderId = order.id

        orderDateState = OrderDateState(order.dateCreated, order.state)

        headerTextCustomer = HeaderOrderText("Datos Cliente")
        contentCustomer = listOf(
            OrderContentTextDetail("Nombre:", "${order.billing.firstName} ${order.billing.lastName}"),
            OrderContentTextDetail("Rut:", order.rut),
            OrderContentTextDetail("Teléfono:", order.billing.phone)
        )

        headerTextShipping = HeaderOrderText("Datos de Envío")
        contentShipping = listOf(
            OrderContentTextDetail("Dirección 1:", order.shipping.address1),
            OrderContentTextDetail("Dirección 2:", order.shipping.address2),
            OrderContentTextDetail("Comuna:", order.shipping.city),
            OrderContentTextDetail("Ciudad:", order.shipping.region),
            OrderContentTextDetail("Método de envío:", order.shipping.methodName),
            OrderContentTextDetail("Método de pago:", order.paymentMethodTitle),
            OrderContentTextDetail("Nota:", order.note)
        )

        headerTextProduct = HeaderOrderText("Productos", "Total pedido: $${order.total}")
        contentProducts = order.products

    }

    var orderId: Int = 0

    // HEADER STATE - DATE
    lateinit var orderDateState: OrderDateState

    // CUSTOMER INFO
    lateinit var headerTextCustomer: HeaderOrderText
    lateinit var contentCustomer: List<OrderContentTextDetail>

    // SHIPPING INFO
    lateinit var headerTextShipping: HeaderOrderText
    lateinit var contentShipping: List<OrderContentTextDetail>

    // PRODUCT INFO
    lateinit var headerTextProduct: HeaderOrderText
    lateinit var contentProducts: List<Product>


}