package cl.gerardomascayano.tdmadmin.ui.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.databinding.OrderItemLoaderBinding

class OrdersLoadAdapter() : LoadStateAdapter<OrdersLoadAdapter.OrderLoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = OrderLoaderViewHolder(
        OrderItemLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OrderLoaderViewHolder, loadState: LoadState) = holder.bind(loadState)

    inner class OrderLoaderViewHolder(private val viewBinding: OrderItemLoaderBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(loadState: LoadState) {

            if (loadState is LoadState.Loading) {

            } else {

            }
        }
    }

}