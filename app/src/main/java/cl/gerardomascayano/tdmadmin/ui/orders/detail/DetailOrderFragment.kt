package cl.gerardomascayano.tdmadmin.ui.orders.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import cl.gerardomascayano.tdmadmin.MainActivity
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.GenericState
import cl.gerardomascayano.tdmadmin.core.OnClickListener
import cl.gerardomascayano.tdmadmin.core.extension.exhaustive
import cl.gerardomascayano.tdmadmin.core.ui.ActivityFragmentContract
import cl.gerardomascayano.tdmadmin.core.ui.IconTypeActivity
import cl.gerardomascayano.tdmadmin.databinding.DetailOrderFragmentBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import cl.gerardomascayano.tdmadmin.ui.orders.OrdersViewModel
import cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter.ContentTextAdapter
import cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter.HeaderOrderStateAdapter
import cl.gerardomascayano.tdmadmin.ui.orders.detail.adapter.HeaderTextAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class DetailOrderFragment : Fragment(), ActivityFragmentContract, OnClickListener<Pair<TypeContent, String>> {

    private val viewModel = viewModels<DetailOrderViewModel>()
    private val ordersViewModel = activityViewModels<OrdersViewModel>()
    private var _viewBinding: DetailOrderFragmentBinding? = null
    private val viewBinding: DetailOrderFragmentBinding
        get() = _viewBinding!!
    private lateinit var headerOrderStateAdapter: HeaderOrderStateAdapter

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

    private fun observeUpdateOrder() {
        if (!viewModel.value.updateOrder.hasActiveObservers()) {
            viewModel.value.updateOrder.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is GenericState.Loading -> {
                        headerOrderStateAdapter.notifyItemChanged(0)
                    }
                    is GenericState.Error -> Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_LONG).show()
                    is GenericState.Success<*> -> {
                        Toast.makeText(requireContext(), "Se actualizó correctamente", Toast.LENGTH_LONG).show()
                        val orderUpdated = (state.data as Order)
                        viewModel.value.orderDateState.state = orderUpdated.state
                    }
                }.exhaustive
            }
        }
    }

    private fun configureRv() {
        // STATE
        headerOrderStateAdapter = HeaderOrderStateAdapter(viewModel.value.orderDateState, this)

        val orderDetailAdapters = listOf(
            headerOrderStateAdapter,

            // CUSTOMER
            HeaderTextAdapter(viewModel.value.headerTextCustomer),
            ContentTextAdapter(viewModel.value.contentCustomer, this),

            // SHIPPING
            HeaderTextAdapter(viewModel.value.headerTextShipping),
            ContentTextAdapter(viewModel.value.contentShipping),

            // PRODUCT
            HeaderTextAdapter(viewModel.value.headerTextProduct),
            ProductsDetailAdapter(viewModel.value.contentProducts),
        )

        val config = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatOrderDetailAdapters = ConcatAdapter(config, orderDetailAdapters)
        viewBinding.rvDetailOrder.addItemDecoration(
            HorizontalDividerDetailOrder(
                ContextCompat.getColor(requireContext(), R.color.divider_gray),
                resources.getDimensionPixelSize(R.dimen.divider_height_detail_product)
            )
        )
        viewBinding.rvDetailOrder.adapter = concatOrderDetailAdapters
    }


    override fun iconLeftToShow(): IconTypeActivity = IconTypeActivity.ARROW_BACK
    override fun onClickListener(item: Pair<TypeContent, String>) {
        val (typeContent, content) = item
        when (typeContent) {
            TypeContent.NO_TYPE -> Unit
            TypeContent.ORDER_STATE -> {
                val states = OrderState.values()
                    .filter { it.id != content }
                    .map { it.description }.toTypedArray()

                AlertDialog.Builder(requireContext())
                    .setTitle("Actualizar estado")
                    .setItems(states) { _, which ->
                        observeUpdateOrder()
                        val stateSelected = OrderState.fromDescription(states[which])
                        viewModel.value.updateStatus(stateSelected.id)
                        ordersViewModel.value.invalidateData()
                    }
                    .create()
                    .show()
            }
            TypeContent.PHONE_NUMBER -> {
                val intent = Intent()
                intent.action = Intent.ACTION_DIAL
                intent.data = Uri.parse("tel: $content")
                startActivity(intent)
            }
        }
    }

    companion object {

        const val ARG_ORDER = "order"

        fun newInstance(order: Order) = DetailOrderFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_ORDER, order)
            }
        }
    }
}