package cl.gerardomascayano.tdmadmin.ui.orders.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gerardomascayano.tdmadmin.MainActivity
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.format
import cl.gerardomascayano.tdmadmin.core.ui.ActivityFragmentContract
import cl.gerardomascayano.tdmadmin.core.ui.IconTypeActivity
import cl.gerardomascayano.tdmadmin.core.ui.MarginItemDecorator
import cl.gerardomascayano.tdmadmin.databinding.DetailOrderFragmentBinding
import cl.gerardomascayano.tdmadmin.domain.order.Order
import cl.gerardomascayano.tdmadmin.domain.order.OrderDateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderFragment : Fragment(), ActivityFragmentContract {

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
        configureRv()
        setOrderData()
    }

    private fun configureRv() {
        viewBinding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rvProducts.addItemDecoration(
            MarginItemDecorator(
                resources.getDimension(R.dimen.margin_vertical_detail_product).toInt(),
                resources.getDimension(R.dimen.margin_lateral_detail_product).toInt()
            )
        )
        viewBinding.rvProducts.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

    }

    private fun setOrderData() {
        with(viewModel.value.order) {

            // ORDER
            (requireActivity() as MainActivity).updateTitle("Pedido: #$id")
            viewBinding.tvDateCreated.text = dateCreated.format(OrderDateUtil.PATTERN_ORDER_DATE_CREATED_LIST)
            viewBinding.tvStatus.text = state.description
            viewBinding.tvStatus.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), state.color))
            viewBinding.tvTotal.text = getString(R.string.order_total_with_title, total)

            // USER
            viewBinding.tvCustomerName.text = getString(R.string.order_customer_name_with_title, billing.firstName, billing.lastName)
            viewBinding.tvCustomerRut.text = getString(R.string.order_customer_rut_with_title, rut)
            viewBinding.tvCustomerPhone.text = getString(R.string.order_customer_phone_with_title, billing.phone)

            // SHIPPING
            viewBinding.tvAddress.text = getString(R.string.order_shipping_address_with_title, shipping.address1)
            viewBinding.tvAddress1.text = getString(R.string.order_shipping_address1_with_title, shipping.address1)
            viewBinding.tvAddress2.text = getString(R.string.order_shipping_address2_with_title, shipping.address2)
            viewBinding.tvComuna.text = getString(R.string.order_shipping_comuna_with_title, shipping.city)
            viewBinding.tvCity.text = getString(R.string.order_shipping_city_with_title, shipping.region)
            viewBinding.tvMethodShipping.text = getString(R.string.order_shipping_method_with_title, shipping.methodName)
            viewBinding.tvNote.text = getString(R.string.order_shipping_note_with_title, note)

            // PAYMENT
            viewBinding.tvMethodPayment.text = getString(R.string.order_payment_method_with_title, paymentMethodTitle)

            // PRODUCTS
            viewBinding.rvProducts.adapter = ProductsDetailAdapter(products)
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

    override fun iconLeftToShow(): IconTypeActivity = IconTypeActivity.ARROW_BACK

}