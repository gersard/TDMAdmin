package cl.gerardomascayano.tdmadmin.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "order")
data class OrderEntity(
    @PrimaryKey(autoGenerate = false) val orderId: Long,
    // TODO Aplicar ENUM
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "date_created") val dateCreated: Date,
    @ColumnInfo(name = "total") val total: Int,
    // TODO Ver opcion de Relation
    @ColumnInfo(name = "customer_id") val customerId: Int,
    @ColumnInfo(name = "customer_note") val customerNote: Int,
    // TODO Shipping
    @ColumnInfo(name = "payment_method") val paymentMethod: String,
    @ColumnInfo(name = "payment_method_title") val paymentMethodTitle: String,
    @ColumnInfo(name = "date_paid") val datePaid: Date,
    // TODO Productos
)