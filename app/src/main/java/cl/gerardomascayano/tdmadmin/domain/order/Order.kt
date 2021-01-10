package cl.gerardomascayano.tdmadmin.domain.order

import android.os.Parcelable
import cl.gerardomascayano.tdmadmin.core.diffutil.DiffUtilComparator
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Order(
    val id: Int,
    val status: OrderStatus,
    val dateCreated: LocalDateTime,
    val datePaid: LocalDateTime?,
    val total: Int,
    //TODO Ver si es necesario obtener el customer entero o solo se usan datos de billing
    val customerId: Int,
    val note: String,
    val billing: Billing,
    val shipping: Shipping,
    val paymentMethod: String,
    val paymentMethodTitle: String,
    val products: List<Product>,
) : DiffUtilComparator, Parcelable {

    override fun getIdentifier(): Int {
        return id
    }

    override fun getContent(): String {
        return toString()
    }

    @Parcelize
    data class Shipping(
        val firstName: String,
        val lastName: String,
        val address1: String,
        val address2: String,
        val city: String,
        val region: String,
        val postCode: String
    ) : Parcelable

    @Parcelize
    data class Billing(
        val firstName: String,
        val lastName: String,
        val address1: String,
        val address2: String,
        val city: String,
        val region: String,
        val postCode: String,
        val email: String,
        val phone: String
    ) : Parcelable

    @Parcelize
    data class Product(
        val id: Int,
        val name: String,
        val productId: Int,
        val quantity: Int,
        val sku: String,
        val price: Int
    ) : Parcelable

}
