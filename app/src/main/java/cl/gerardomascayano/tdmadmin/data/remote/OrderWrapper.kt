package cl.gerardomascayano.tdmadmin.data.remote

import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderWrapper {

    fun ordersResponseToOrder(ordersResponse: List<OrderResponse>): List<Order> =
        ordersResponse.map { orderResponseToOrder(it) }

    fun orderResponseToOrder(orderResponse: OrderResponse): Order {
        return Order(
            orderResponse.id,
            OrderStatus.from(orderResponse.status),
            LocalDateTime.parse(orderResponse.dateCreated, DateTimeFormatter.ISO_DATE_TIME),
            if (orderResponse.datePaid != null) LocalDateTime.parse(orderResponse.datePaid, DateTimeFormatter.ISO_DATE_TIME) else null,
            orderResponse.total,
            orderResponse.customerId,
            orderResponse.customerNote,
            billingResponseToBilling(orderResponse.billing),
            shippingResponseToShipping(orderResponse.shipping),
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

    private fun shippingResponseToShipping(shippingResponse: OrderResponse.Shipping): Order.Shipping {
        return Order.Shipping(
            shippingResponse.firstName,
            shippingResponse.lastName,
            shippingResponse.address1,
            shippingResponse.address2,
            shippingResponse.city,
            shippingResponse.state,
            shippingResponse.postcode
        )
    }

    private fun productsResponseToProducts(productsResponse: List<OrderResponse.Product>): List<Order.Product> =
        productsResponse.map { productResponseToProduct(it) }

    private fun productResponseToProduct(productResponse: OrderResponse.Product): Order.Product {
        return Order.Product(
            productResponse.id,
            productResponse.name,
            productResponse.id,
            productResponse.quantity,
            productResponse.sku,
            productResponse.price
        )
    }
}