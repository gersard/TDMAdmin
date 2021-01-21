package cl.gerardomascayano.tdmadmin.core.extension

import android.animation.ObjectAnimator
import android.view.View


fun View.visible(withAnimation: Boolean = false, durationMs: Long = 300) {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
    if (withAnimation) {
        ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
            .setDuration(durationMs)
            .start()
    }
}

fun View.gone(withAnimation: Boolean = false, durationMs: Long = 300) {
    if (this.visibility != View.GONE) this.visibility = View.GONE
    if (withAnimation) {
        ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
            .setDuration(durationMs)
            .start()
    }
}

fun View.invisible(withAnimation: Boolean = false, durationMs: Long = 300) {
    if (this.visibility != View.INVISIBLE) this.visibility = View.INVISIBLE
    if (withAnimation) {
        ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
            .setDuration(durationMs)
            .start()
    }
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}