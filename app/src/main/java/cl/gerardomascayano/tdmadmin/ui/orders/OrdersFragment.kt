package cl.gerardomascayano.tdmadmin.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.exhaustive
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.core.ui.MarginItemDecorator
import cl.gerardomascayano.tdmadmin.databinding.FragmentOrdersBinding
import cl.gerardomascayano.tdmadmin.ui.orders.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import timber.log.Timber

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
        fetchOrders()
    }

    private fun listeningOrdersState() {
        ordersAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Loading -> viewBinding.srlOrders.isRefreshing = true
                is LoadState.NotLoading -> viewBinding.srlOrders.isRefreshing = false
                is LoadState.Error -> Toast.makeText(requireContext(), "ERROR ${(state.refresh as LoadState.Error).error.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun fetchOrders() {
        viewLifecycleOwner.lifecycleScope.launch {
            ordersViewModel.value.fetchOrders()
                .collectLatest {
                    ordersAdapter.submitData(it)
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