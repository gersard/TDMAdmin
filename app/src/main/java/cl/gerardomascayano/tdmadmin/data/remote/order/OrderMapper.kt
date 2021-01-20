package cl.gerardomascayano.tdmadmin.data.remote.order

import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import cl.gerardomascayano.tdmadmin.domain.order.Product
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderMapper {

    // NETWORK -> DOMAIN
    fun ordersResponseToOrder(ordersResponse: List<OrderResponse>): List<Order> =
        ordersResponse.map { orderResponseToOrder(it) }

    fun orderResponseToOrder(orderResponse: OrderResponse): Order {
        return Order(
            orderResponse.id,
            OrderState.from(orderResponse.status),
            LocalDateTime.parse(orderResponse.dateCreated, DateTimeFormatter.ISO_DATE_TIME),
            if (orderResponse.datePaid != null) LocalDateTime.parse(orderResponse.datePaid, DateTimeFormatter.ISO_DATE_TIME) else null,
            orderResponse.total,
            orderResponse.customerId,
            orderResponse.metaData.firstOrNull { it.key == OrderConstant.META_DATA_RUT }?.value,
            orderResponse.customerNote,
            billingResponseToBilling(orderResponse.billing),
            shippingResponseToShipping(orderResponse.shipping, orderResponse.shippingDetail?.firstOrNull()),
            orderResponse.paymentMethod,
            orderResponse.paymentMethodTitle,
            productsResponseToProducts(orderResponse.products)
        )
    }

    private fun billingResponseToBilling(billingResponse: OrderResponse.Billing): Order.Billing {
        return Order.Billing(
            billingResponse.firstName,
            billingResponse.lastName,
            billingResponse.address1,
            billingResponse.address2,
            billingResponse.city,
            billingResponse.state,
            billingResponse.postcode,
            billingResponse.email,
            billingResponse.phone
        )
    }

    private fun shippingResponseToShipping(shippingResponse: OrderResponse.Shipping, shippingDetail: OrderResponse.ShippingDetail?): Order.Shipping {
        return Order.Shipping(
            shippingDetail?.methodTitle,
            shippingResponse.firstName,
            shippingResponse.lastName,
            shippingResponse.address1,
            shippingResponse.address2,
            shippingResponse.city,
            shippingResponse.state,
            shippingResponse.postcode
        )
    }

    private fun productsResponseToProducts(productsResponse: List<OrderResponse.Product>): List<Product> =
        productsResponse.map { productResponseToProduct(it) }

    private fun productResponseToProduct(productResponse: OrderResponse.Product): Product {
        return Product(
            productResponse.id,
            productResponse.name,
            productResponse.id,
            productResponse.quantity,
            productResponse.sku,
            productResponse.price
        )
    }
}