package cl.gerardomascayano.tdmadmin.core.diffutil

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T : DiffUtilComparator> {

    val itemCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.getIdentifier() == newItem.getIdentifier()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.getContent() == newItem.getContent()
        }

    }




}