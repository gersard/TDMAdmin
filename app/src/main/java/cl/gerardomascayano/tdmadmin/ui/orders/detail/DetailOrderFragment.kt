package cl.gerardomascayano.tdmadmin.ui.orders.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.databinding.DetailOrderFragmentBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderFragment : Fragment() {

    private val viewModel = viewModels<DetailOrderViewModel>()
    private var _viewBinding: DetailOrderFragmentBinding? = null
    private val viewBinding: DetailOrderFragmentBinding
        get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { viewModel.value.order = it.getParcelable(ARG_ORDER)!! }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = DetailOrderFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOrderData()
    }

    private fun setOrderData() {
        with(viewModel.value.order){
            viewBinding.tvCustomerName.text = getString(R.string.order_customer_name,billing.firstName,billing.lastName)
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