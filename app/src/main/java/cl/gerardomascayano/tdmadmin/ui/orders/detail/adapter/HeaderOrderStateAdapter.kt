package cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.core.extension.format
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.databinding.HeaderStateDetailOrderBinding
import cl.gerardomascayano.tdmadmin.databinding.HeaderTextDetailOrderBinding
import cl.gerardomascayano.tdmadmin.domain.order.OrderDateUtil
import cl.gerardomascayano.tdmadmin.domain.order.detail.HeaderOrderText
import cl.gerardomascayano.tdmadmin.domain.order.detail.OrderDateState

class HeaderOrderStateAdapter(private val header: OrderDateState) : RecyclerView.Adapter<HeaderOrderStateAdapter.HeaderTextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HeaderTextViewHolder(
        HeaderStateDetailOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HeaderTextViewHolder, position: Int) = holder.bind(header)

    override fun getItemCount(): Int = 1


    inner class HeaderTextViewHolder(private val viewbinding: HeaderStateDetailOrderBinding) : RecyclerView.ViewHolder(viewbinding.root) {

        fun bind(orderDateState: OrderDateState) {
            viewbinding.tvDateCreated.text = orderDateState.dateCreated.format(OrderDateUtil.PATTERN_ORDER_DATE_CREATED_LIST)

            viewbinding.tvStatus.text = orderDateState.state.description
            viewbinding.tvStatus.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(viewbinding.root.context, orderDateState.state.color))
        }

    }
}