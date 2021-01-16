package cl.gerardomascayano.tdmadmin.ui.orders.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.databinding.ItemProductDetailBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.Product

class ProductsDetailAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductsDetailAdapter.ProductDetailViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductDetailViewHolder(
        ItemProductDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) = holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

    inner class ProductDetailViewHolder(val viewBinding: ItemProductDetailBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindProduct(product: Product) {
            with(viewBinding.root.context) {
                viewBinding.tvDescription.text = getString(R.string.product_detail_description, product.name)
                viewBinding.tvSku.text = getString(R.string.product_detail_sku, product.sku)
                viewBinding.tvQuantity.text = getString(R.string.product_detail_quantity, product.quantity)
                viewBinding.tvUnitPrice.text = getString(R.string.product_detail_unit_price, product.price)
            }
        }

    }

}