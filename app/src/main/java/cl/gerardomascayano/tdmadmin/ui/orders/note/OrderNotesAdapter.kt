package cl.gerardomascayano.tdmadmin.ui.orders.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.gerardomascayano.tdmadmin.core.extension.format
import cl.gerardomascayano.tdmadmin.databinding.ItemOrderNoteBinding
import cl.gerardomascayano.tdmadmin.domain.order.OrderDateUtil
import cl.gerardomascayano.tdmadmin.domain.order.note.OrderNote

class OrderNotesAdapter : RecyclerView.Adapter<OrderNotesAdapter.OrderNoteViewHolder>() {

    private val listNotes = mutableListOf<OrderNote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderNoteViewHolder(
        ItemOrderNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OrderNoteViewHolder, position: Int) = holder.bindNote(listNotes[position])

    override fun getItemCount(): Int = listNotes.size

    fun addNoteList(notes: List<OrderNote>) {
        listNotes.addAll(notes)
        notifyItemRangeInserted(0, listNotes.size)
    }

    fun addNewNote(note: OrderNote) {
        listNotes.add(0, note)
        notifyItemInserted(0)
    }

    inner class OrderNoteViewHolder(private val viewBinding: ItemOrderNoteBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bindNote(note: OrderNote) {
            viewBinding.tvNoteType.text = note.noteType.description
            viewBinding.tvNote.text = note.note
            viewBinding.tvNoteDateCreated.text = note.dateCreated.format(OrderDateUtil.PATTERN_ORDER_DATE_CREATED_LIST)
        }
    }

}