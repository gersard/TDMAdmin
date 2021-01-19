package cl.gerardomascayano.tdmadmin.ui.orders.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.gerardomascayano.tdmadmin.core.GenericState
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import cl.gerardomascayano.tdmadmin.domain.order.OrdersUseCase
import cl.gerardomascayano.tdmadmin.domain.order.Product
import cl.gerardomascayano.tdmadmin.domain.order.detail.HeaderOrderText
import cl.gerardomascayano.tdmadmin.domain.order.detail.OrderContentTextDetail
import cl.gerardomascayano.tdmadmin.domain.order.detail.OrderDateState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailOrderViewModel @ViewModelInject constructor(private val useCase: OrdersUseCase) : ViewModel() {

    fun generateData(order: Order) {
        orderId = order.id

        orderDateState = OrderDateState(order.dateCreated, order.state, false, TypeContent.ORDER_STATE, true)

        headerTextCustomer = HeaderOrderText("Datos Cliente")
        contentCustomer = listOf(
            OrderContentTextDetail("Nombre:", "${order.billing.firstName} ${order.billing.lastName}"),
            OrderContentTextDetail("Rut:", order.rut ?: "---"),
            OrderContentTextDetail("Teléfono:", order.billing.phone, TypeContent.PHONE_NUMBER, true)
        )

        headerTextShipping = HeaderOrderText("Datos de Envío")
        contentShipping = listOf(
            OrderContentTextDetail("Dirección 1:", order.shipping.address1),
            OrderContentTextDetail("Dirección 2:", order.shipping.address2),
            OrderContentTextDetail("Comuna:", order.shipping.city),
            OrderContentTextDetail("Ciudad:", order.shipping.region),
            OrderContentTextDetail("Método de envío:", order.shipping.methodName ?: "---"),
            OrderContentTextDetail("Método de pago:", order.paymentMethodTitle),
            OrderContentTextDetail("Nota:", order.note)
        )

        headerTextProduct = HeaderOrderText("Productos", "Total pedido: $${order.total}")
        contentProducts = order.products

    }

    private val _updateOrder = MutableLiveData<GenericState>()
    val updateOrder: LiveData<GenericState> get() = _updateOrder

    fun updateStatus(stateId: String) {
        viewModelScope.launch() {
            orderDateState.isUpdating = true
            _updateOrder.value = GenericState.Loading(true)
            val stateResult = useCase.updateStatus(orderId, stateId)
            orderDateState.isUpdating = false
            _updateOrder.value = stateResult
            _updateOrder.value = GenericState.Loading(false)
        }
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