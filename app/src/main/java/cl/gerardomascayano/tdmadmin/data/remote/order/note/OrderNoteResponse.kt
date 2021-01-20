package cl.gerardomascayano.tdmadmin.data.remote.order.note

import com.google.gson.annotations.SerializedName

data class OrderNoteResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("note") val note: String,
    @SerializedName("customer_note") val customerNote: Boolean
)
