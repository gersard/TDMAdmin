package cl.gerardomascayano.tdmadmin.ui.orders.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gerardomascayano.tdmadmin.MainActivity
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.OnClickListener
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.core.ui.*
import cl.gerardomascayano.tdmadmin.databinding.FragmentOrdersBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.list.OrdersViewState
import cl.gerardomascayano.tdmadmin.ui.orders.detail.DetailOrderFragment
import cl.gerardomascayano.tdmadmin.ui.orders.list.adapter.OrderListAdapter
import cl.gerardomascayano.tdmadmin.ui.orders.list.adapter.OrderLoaderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment(), OnClickListener<Order>, ActivityFragmentContract {

    private val ordersViewModel = activityViewModels<OrdersViewModel>()
    private var _viewBinding: FragmentOrdersBinding? = null
    private val viewBinding: FragmentOrdersBinding
        get() = _viewBinding!!
    private lateinit var orderConcatAdapter: ConcatAdapter
    private lateinit var orderListAdapter: OrderListAdapter
    private lateinit var orderLoaderAdapter: OrderLoaderAdapter

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
        (requireActivity() as MainActivity).updateTitle("Órdenes")
        initAdapter()
        configureRv()
        fetchOrders()
        viewBinding.srlOrders.setOnRefreshListener {
            ordersViewModel.value.clearOrders()
            fetchOrders()
        }
    }


    private fun fetchOrders() {
        viewLifecycleOwner.lifecycleScope.launch {
            ordersViewModel.value.fetchOrderss()
                .collect {
                    when (it) {
                        is OrdersViewState.Loading ->
                            if (it.isLoading) orderConcatAdapter.addAdapter(orderLoaderAdapter)
                            else orderConcatAdapter.removeAdapter(orderLoaderAdapter)
                        is OrdersViewState.Error -> Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
                        is OrdersViewState.Success -> orderListAdapter.submitOrders(it.data)
                        OrdersViewState.EndPageReached -> TODO()
                        OrdersViewState.EmptyList -> emptyOrders()
                    }
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

        orderConcatAdapter = ConcatAdapter(orderListAdapter)
        viewBinding.rvOrders.adapter = orderConcatAdapter
    }

    private fun initAdapter() {
        orderListAdapter = OrderListAdapter(this)
        orderLoaderAdapter = OrderLoaderAdapter()
    }

    fun filterOrders(text: String) {
        ordersViewModel.value.currentFilterText = text
        ordersViewModel.value.invalidateData()
        fetchOrders()
    }


    override fun onClickListener(item: Order) {
        (activity as? MainActivity)?.replaceFragment(DetailOrderFragment.newInstance(item), true, AnimationType.SLIDE)
    }


    override fun iconLeftToShow(): IconLeftTypeActivity = IconLeftTypeActivity.HAMBURGUER
    override fun iconRightToShow(): IconRightTypeActivity = IconRightTypeActivity.SEARCH

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        fun newInstance() = OrdersFragment()
    }


}