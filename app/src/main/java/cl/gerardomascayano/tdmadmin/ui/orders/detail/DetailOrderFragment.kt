package cl.gerardomascayano.tdmadmin.ui.orders.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.MainActivity
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.format
import cl.gerardomascayano.tdmadmin.core.ui.ActivityFragmentContract
import cl.gerardomascayano.tdmadmin.core.ui.IconTypeActivity
import cl.gerardomascayano.tdmadmin.core.ui.MarginItemDecorator
import cl.gerardomascayano.tdmadmin.databinding.DetailOrderFragmentBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderDateUtil
import cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter.ContentTextAdapter
import cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter.HeaderOrderStateAdapter
import cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter.HeaderTextAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderFragment : Fragment(), ActivityFragmentContract {

    private val viewModel = viewModels<DetailOrderViewModel>()
    private var _viewBinding: DetailOrderFragmentBinding? = null
    private val viewBinding: DetailOrderFragmentBinding
        get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { viewModel.value.generateData(it.getParcelable(ARG_ORDER)!!) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = DetailOrderFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRv()
        (requireActivity() as MainActivity).updateTitle("Pedido: #${viewModel.value.orderId}")
    }

    private fun configureRv() {
        val orderDetailAdapters = listOf(
            // STATE
            HeaderOrderStateAdapter(viewModel.value.orderDateState),

            // CUSTOMER
            HeaderTextAdapter(viewModel.value.headerTextCustomer),
            ContentTextAdapter(viewModel.value.contentCustomer),

            // SHIPPING
            HeaderTextAdapter(viewModel.value.headerTextShipping),
            ContentTextAdapter(viewModel.value.contentShipping),

            // PRODUCT
            HeaderTextAdapter(viewModel.value.headerTextProduct),
            ProductsDetailAdapter(viewModel.value.contentProducts),
        )

        val concatOrderDetailAdapters = ConcatAdapter(orderDetailAdapters)
        viewBinding.rvDetailOrder.adapter = concatOrderDetailAdapters
    }

    companion object {

        const val ARG_ORDER = "order"

        fun newInstance(order: Order) = DetailOrderFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_ORDER, order)
            }
        }
    }

    override fun iconLeftToShow(): IconTypeActivity = IconTypeActivity.ARROW_BACK

}