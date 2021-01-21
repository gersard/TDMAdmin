package cl.gerardomascayano.tdmadmin.data.remote.order.note

import com.google.gson.annotations.SerializedName

class OrderNoteCreate(
    val note: String,
    @SerializedName("customer_note")
    val customerNote: Boolean
)