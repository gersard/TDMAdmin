package cl.gerardomascayano.tdmadmin.data.remote

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("date_created")
    val dateCreated: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("customer_id")
    val customerId: Int,
    @SerializedName("customer_note")
    val customerNote: String,
    @SerializedName("billing")
    val billing: Billing,
    @SerializedName("shipping")
    val shipping: Shipping,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_method_title")
    val paymentMethodTitle: String,
    @SerializedName("date_paid")
    val datePaid: String?,
    @SerializedName("line_items")
    val products: List<Product>,
    @SerializedName("shipping_lines")
    val shippingDetail: List<ShippingDetail>,
    @SerializedName("meta_data")
    val metaData: List<MetaData>

) {
    data class Billing(
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("address_1")
        val address1: String,
        @SerializedName("address_2")
        val address2: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("postcode")
        val postcode: String
    )

    data class Shipping(
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("address_1")
        val address1: String,
        @SerializedName("address_2")
        val address2: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("postcode")
        val postcode: String
    )

    data class Product(
        @SerializedName("product_id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("quantity")
        val quantity: Int,
        @SerializedName("total")
        val total: Int,
        @SerializedName("sku")
        val sku: String,
        @SerializedName("price")
        val price: Int
    )

    data class ShippingDetail(
        @SerializedName("method_title")
        val methodTitle: String,
        @SerializedName("total")
        val total: Int
    )

    data class MetaData(
        @SerializedName("id")
        val id: Int,
        @SerializedName("key")
        val key: String,
        @SerializedName("value")
        val value: String
    )
}

