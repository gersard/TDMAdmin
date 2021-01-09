package cl.gerardomascayano.tdmadmin.ui.orders.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.format
import cl.gerardomascayano.tdmadmin.databinding.ItemOrderBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderDateUtil

class OrdersAdapter() : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    private val orders = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderViewHolder(
        ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.setOrder(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    fun addOrders(newOrders: List<Order>) {
        if (newOrders.isNotEmpty()) {
            val currentSize = this.orders.size
            this.orders.addAll(newOrders)
            notifyItemRangeInserted(currentSize, currentSize + newOrders.size - 1)
        }
    }

    inner class OrderViewHolder(private val viewBinding: ItemOrderBinding) : RecyclerView.ViewHolder(viewBinding.root), View.OnClickListener {

        init {
            viewBinding.ibDetail.setOnClickListener(this)
        }

        fun setOrder(order: Order) {
            viewBinding.root.context.run {
                viewBinding.tvOrderId.text = getString(R.string.order_id, order.id)
                viewBinding.tvCustomerName.text = getString(R.string.order_customer_name, order.billing.firstName, order.billing.lastName)
                viewBinding.tvDateCreatedOrder.text = order.dateCreated.format(OrderDateUtil.PATTERN_ORDER_DATE_CREATED_LIST)
                viewBinding.tvStatus.text = order.status.description
                viewBinding.tvStatus.setBackgroundColor(Color.parseColor(order.status.color))
            }

        }

        override fun onClick(v: View?) {

        }

    }

}