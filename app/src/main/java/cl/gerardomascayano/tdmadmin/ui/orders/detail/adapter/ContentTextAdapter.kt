package cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.databinding.ContentTextDetailOrderBinding
import cl.gerardomascayano.tdmadmin.domain.order.detail.OrderContentTextDetail
import cl.gerardomascayano.tdmadmin.ui.orders.detail.OrderDetailRowType

class ContentTextAdapter(private val content: List<OrderContentTextDetail>) : RecyclerView.Adapter<ContentTextAdapter.OrderContentViewHolder>() {

    override fun getItemViewType(position: Int): Int = OrderDetailRowType.CONTENT_TEXT.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderContentViewHolder(
        ContentTextDetailOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OrderContentViewHolder, position: Int) = holder.bind(content[position])

    override fun getItemCount(): Int = content.size

    inner class OrderContentViewHolder(private val viewbinding: ContentTextDetailOrderBinding) : RecyclerView.ViewHolder(viewbinding.root) {

        fun bind(content: OrderContentTextDetail) {
            viewbinding.tvTitle.text = content.key
            viewbinding.tvDetail.text = content.value
        }

    }
}