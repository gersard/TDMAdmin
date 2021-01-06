package cl.gerardomascayano.tdmadmin.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.databinding.FragmentOrdersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private var _viewBinding: FragmentOrdersBinding? = null
    private val viewBinding: FragmentOrdersBinding
        get() = _viewBinding!!
    private val ordersViewModel = viewModels<OrdersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentOrdersBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ordersViewModel.value.getOrders()
    }

    companion object {
        fun newInstance() = OrdersFragment()
    }

}