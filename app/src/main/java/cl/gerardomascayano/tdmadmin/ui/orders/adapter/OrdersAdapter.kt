package cl.gerardomascayano.tdmadmin.ui.orders.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.diffutil.DiffCallback
import cl.gerardomascayano.tdmadmin.core.extension.format
import cl.gerardomascayano.tdmadmin.databinding.ItemOrderBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderDateUtil

class OrdersAdapter(val clickListener: ClickListener) : PagingDataAdapter<Order, OrdersAdapter.OrderViewHolder>(DiffCallback<Order>().itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderViewHolder(
        ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.setOrder(getItem(position)!!)
    }

    interface ClickListener {
        fun onOrderClickListener(order: Order)
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

        override fun onClick(v: View?) {
            clickListener.onOrderClickListener(getItem(bindingAdapterPosition)!!)
        }

    }

}


/**
 * DateState
 *  - DateCreated
 *  - Status
 *
 * HeaderText
 *  - Title
 *  - Campos (key, value)
 *
 * HeaderProducts
 *  - Title
 *  - Productos
 */

































