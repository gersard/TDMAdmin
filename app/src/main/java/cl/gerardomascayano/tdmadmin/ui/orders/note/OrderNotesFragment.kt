package cl.gerardomascayano.tdmadmin.ui.orders.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.databinding.OrderNotesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderNotesFragment : DialogFragment(), View.OnClickListener {

    private val viewModel = viewModels<OrderNotesViewModel>()
    private var _viewBinding: OrderNotesFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = OrderNotesFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeningViewState()
        viewBinding.btnAnadir.setOnClickListener(this)
        viewBinding.btnCancelar.setOnClickListener(this)
    }

    private fun listeningViewState() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.value.viewState.collect {
                when (it) {
                    OrderNoteViewState.LIST -> {
                        viewBinding.groupListNote.visible()
                        viewBinding.groupAddNote.invisible()
                    }
                    OrderNoteViewState.ADD -> {
                        viewBinding.groupListNote.invisible()
                        viewBinding.groupAddNote.visible()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


    override fun onClick(v: View?) {
        if (v?.id == viewBinding.btnAnadir.id) {
            if (viewModel.value.shouldSendNote()) {
                sendNote()
            } else {
                viewModel.value.toggleViewState()
            }
        } else if (v?.id == viewBinding.btnCancelar.id) {
            if (viewModel.value.shouldClose()) activity?.onBackPressed() else viewModel.value.toggleViewState()
        }
    }

    private fun loadNotes() {

    }

    private fun sendNote() {

    }

    companion object {
        private const val ARG_ORDER_ID = "order_id"
        fun newInstance(orderId: Int) = OrderNotesFragment().apply {
            arguments = Bundle().apply { putInt(ARG_ORDER_ID, orderId) }
        }
    }
}