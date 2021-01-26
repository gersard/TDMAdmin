package cl.gerardomascayano.tdmadmin.ui.orders.list.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.OnClickListener
import cl.gerardomascayano.tdmadmin.core.diffutil.DiffCallback
import cl.gerardomascayano.tdmadmin.core.extension.format
import cl.gerardomascayano.tdmadmin.databinding.ItemOrderBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderDateUtil

class OrderListAdapter(val clickListener: OnClickListener<Order>) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    private val orderList = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderViewHolder(
        ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) = holder.setOrder(orderList[position])

    override fun getItemCount(): Int = orderList.size

    fun submitOrders(orders: List<Order>) {
        val diffCallback = DiffCallback(orderList, orders)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        orderList.addAll(orders)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class OrderViewHolder(private val viewBinding: ItemOrderBinding) : RecyclerView.ViewHolder(viewBinding.root), View.OnClickListener {

        init {
            viewBinding.ibDetail.setOnClickListener(this)
        }

        fun setOrder(order: Order) {
            with(viewBinding.root.context) {
                viewBinding.tvOrderId.text = getString(R.string.order_id, order.id)
                viewBinding.tvCustomerName.text = getString(R.string.order_customer_name, order.billing.firstName, order.billing.lastName)
                viewBinding.tvDateCreatedOrder.text = order.dateCreated.format(OrderDateUtil.PATTERN_ORDER_DATE_CREATED_LIST)
                viewBinding.tvStatus.text = order.state.description
                viewBinding.tvStatus.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, order.state.color))
            }

        }

        override fun onClick(v: View?) = clickListener.onClickListener(orderList[bindingAdapterPosition])

    }

}