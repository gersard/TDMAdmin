package cl.gerardomascayano.tdmadmin.ui.orders.detail

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class HorizontalDividerDetailOrder(
    @ColorInt color: Int,
    private val heightInPixels: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = color
        paint.isAntiAlias = true
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + heightInPixels
            val adapterPosition = parent.getChildAdapterPosition(child)
            val viewType = parent.adapter!!.getItemViewType(adapterPosition)
            Timber.d("VIEW TYPE $viewType")
            if (viewType == OrderDetailRowType.CONTENT_PRODUCT.ordinal) {
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            }
        }
    }
}

