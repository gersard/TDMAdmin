package cl.gerardomascayano.tdmadmin.ui.orders.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cl.gerardomascayano.tdmadmin.R
import cl.gerardomascayano.tdmadmin.core.extension.gone
import cl.gerardomascayano.tdmadmin.core.extension.invisible
import cl.gerardomascayano.tdmadmin.core.extension.visible
import cl.gerardomascayano.tdmadmin.core.ui.MarginItemDecorator
import cl.gerardomascayano.tdmadmin.core.ui.ScreenSize
import cl.gerardomascayano.tdmadmin.databinding.OrderNotesFragmentBinding
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNote
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNoteState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderNotesFragment : DialogFragment(), View.OnClickListener {

    private val viewModel = viewModels<OrderNotesViewModel>()
    private var _viewBinding: OrderNotesFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var notesAdapter: OrderNotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.value.orderId = it.getInt(ARG_ORDER_ID)
        }
    }

    override fun onStart() {
        super.onStart()
//        setStyle(STYLE_NO_FRAME, 0)
        val (width, height) = ScreenSize.get(requireActivity())
        dialog?.window?.setLayout((width * 0.9).toInt(), (height * 0.8).toInt())
        dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_corner_radius_medium))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _viewBinding = OrderNotesFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRv()
        listeningViewState()
        viewBinding.tvTitle.text = "Notas del pedido: #${viewModel.value.orderId}"
        viewBinding.btnAnadir.setOnClickListener(this)
        viewBinding.btnCancelar.setOnClickListener(this)
        loadNotes()
    }

    private fun configureRv() {
        viewBinding.rvOrderNotes.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.rvOrderNotes.addItemDecoration(
            MarginItemDecorator(
                resources.getDimension(R.dimen.margin_vertical_note).toInt(),
                resources.getDimension(R.dimen.margin_horizontal_note).toInt()
            )
        )
        viewBinding.rvOrderNotes.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        notesAdapter = OrderNotesAdapter()
        viewBinding.rvOrderNotes.adapter = notesAdapter
    }

    private fun listeningViewState() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.value.viewState.collect {
                when (it) {
                    OrderNoteViewState.LIST -> {
                        viewBinding.groupListNote.visible(true)
                        viewBinding.groupAddNote.invisible(true)
                    }
                    OrderNoteViewState.ADD -> {
                        viewBinding.groupListNote.invisible(true)
                        viewBinding.groupAddNote.visible(true)
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
            if (viewModel.value.shouldClose()) dismiss() else viewModel.value.toggleViewState()
        }
    }

    private fun loadNotes() {
        viewModel.value.listOrderNotes()
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.value.orderNoteState.collect { orderNoteState ->
                when (orderNoteState) {
                    is OrderNoteState.Loading -> if (orderNoteState.isLoading) viewBinding.pbLoading.visible() else viewBinding.pbLoading.gone()
                    is OrderNoteState.Error -> Toast.makeText(requireContext(), orderNoteState.errorMessage, Toast.LENGTH_LONG).show()
                    is OrderNoteState.Success -> notesAdapter.addNoteList(orderNoteState.data)
                }
            }
        }
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