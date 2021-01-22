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
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gerardomascayano.tdmadmin.MainActivity
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.core.ui.*
import cl.gerardomascayano.tdmadmin.databinding.FragmentOrdersBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.ui.orders.adapter.OrdersAdapter
import cl.gerardomascayano.tdmadmin.ui.orders.adapter.OrdersLoadAdapter
import cl.gerardomascayano.tdmadmin.ui.orders.detail.DetailOrderFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OrdersFragment : Fragment(), OrdersAdapter.ClickListener, ActivityFragmentContract {

    private val ordersViewModel = activityViewModels<OrdersViewModel>()
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
        (requireActivity() as MainActivity).updateTitle("Órdenes")
        initAdapter()
        configureRv()
        listeningOrdersState()
        fetchOrders()
        viewBinding.srlOrders.setOnRefreshListener {
            ordersViewModel.value.clearOrders()
            fetchOrders()
        }
    }

    private fun listeningOrdersState() {
        ordersAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Loading -> viewBinding.srlOrders.post { viewBinding.srlOrders.isRefreshing = true }
                is LoadState.NotLoading -> {
                    viewBinding.srlOrders.isRefreshing = false
                    if (state.refresh.endOfPaginationReached && ordersAdapter.itemCount < 1) emptyOrders()
                }
                is LoadState.Error -> {
                    viewBinding.srlOrders.post { viewBinding.srlOrders.isRefreshing = false }
                    Toast.makeText(requireContext(), "Ha ocurrido un error. Comprueba tu conexión y vuelve a intentar", Toast.LENGTH_LONG)
                        .show()
                }
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
        viewBinding.rvOrders.adapter = ordersAdapter.withLoadStateFooter(OrdersLoadAdapter())
    }

    private fun initAdapter() {
        ordersAdapter = OrdersAdapter(this)
    }

    fun filterOrders(text: String) {
        ordersViewModel.value.currentFilterText = text
        ordersViewModel.value.invalidateData()
        fetchOrders()
    }

    override fun onOrderClickListener(order: Order) {
        (activity as? MainActivity)?.replaceFragment(DetailOrderFragment.newInstance(order), true, AnimationType.SLIDE)
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