package cl.gerardomascayano.tdmadmin.domain.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val productId: Int,
    val quantity: Int,
    val sku: String?,
    val price: Int
) : Parcelable
