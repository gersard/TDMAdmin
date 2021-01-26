package cl.gerardomascayano.tdmadmin.ui.orders.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.databinding.OrderItemLoaderBinding

class OrderLoaderAdapter : RecyclerView.Adapter<OrderLoaderAdapter.OrderLoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderLoaderViewHolder(
        OrderItemLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OrderLoaderViewHolder, position: Int) = Unit

    override fun getItemCount(): Int = 1


    inner class OrderLoaderViewHolder(viewBinding: OrderItemLoaderBinding) : RecyclerView.ViewHolder(viewBinding.root)


}