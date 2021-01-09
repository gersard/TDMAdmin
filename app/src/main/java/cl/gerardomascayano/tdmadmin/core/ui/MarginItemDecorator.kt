package cl.gerardomascayano.tdmadmin.core.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(private val marginVertical: Int, private val marginHorizontal: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            top = marginVertical
            bottom = marginVertical
            left = marginHorizontal
            right = marginHorizontal
        }
    }

}