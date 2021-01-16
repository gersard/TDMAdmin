package cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.databinding.HeaderTextDetailOrderBinding
import cl.gerardomascayano.tdmadmin.domain.order.detail.HeaderOrderText

class HeaderTextAdapter(private val header: HeaderOrderText) : RecyclerView.Adapter<HeaderTextAdapter.HeaderTextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HeaderTextViewHolder(
        HeaderTextDetailOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HeaderTextViewHolder, position: Int) = holder.bind(header)

    override fun getItemCount(): Int = 1


    inner class HeaderTextViewHolder(private val viewbinding: HeaderTextDetailOrderBinding) : RecyclerView.ViewHolder(viewbinding.root) {

        fun bind(header: HeaderOrderText) {
            viewbinding.tvHeaderText.text = header.title
            if (header.subtitle != null) {
                viewbinding.tvHeaderDetail.visible()
                viewbinding.tvHeaderDetail.text = header.subtitle
            } else {
                viewbinding.tvHeaderDetail.invisible()
            }
        }

    }
}