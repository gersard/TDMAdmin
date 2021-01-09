package cl.gerardomascayano.tdmadmin.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.exhaustive
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.core.ui.MarginItemDecorator
import cl.gerardomascayano.tdmadmin.databinding.FragmentOrdersBinding
import cl.gerardomascayano.tdmadmin.domain.order.OrderState
import cl.gerardomascayano.tdmadmin.ui.orders.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private val ordersViewModel = viewModels<OrdersViewModel>()
    private var _viewBinding: FragmentOrdersBinding? = null
    private val viewBinding: FragmentOrdersBinding
        get() = _viewBinding!!
    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentOrdersBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        configureRv()
        listeningOrdersState()
        ordersViewModel.value.getOrders()
    }

    private fun listeningOrdersState() {
        viewLifecycleOwner.lifecycleScope.launch {
            ordersViewModel.value.orderState.drop(1).collect { state ->
                when (state) {
                    is OrderState.Loading -> viewBinding.srlOrders.isRefreshing = state.isLoading
                    is OrderState.Success -> ordersAdapter.addOrders(state.data)
                    is OrderState.Failure -> viewBinding.tvEmptyOrders.text = requireContext().getString(state.messageRes)
                    is OrderState.Empty -> emptyOrders()
                }.exhaustive
            }
        }
    }

    private fun emptyOrders() {
        viewBinding.rvOrders.invisible()
        viewBinding.tvEmptyOrders.visible()
    }

    private fun configureRv() {
        viewBinding.rvOrders.setHasFixedSize(true)
        viewBinding.rvOrders.addItemDecoration(
            MarginItemDecorator(
                resources.getDimension(R.dimen.margin_vertical_order_item).toInt(), resources.getDimension(R.dimen.margin_lateral_order_item).toInt()
            )
        )
        viewBinding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rvOrders.adapter = ordersAdapter
    }

    private fun initAdapter() {
        ordersAdapter = OrdersAdapter()
    }

    companion object {
        fun newInstance() = OrdersFragment()
    }

}