package cl.gerardomascayano.tdmadmin.core.extension

import android.animation.ObjectAnimator
import android.view.View


fun View.visible(withAnimation: Boolean = false) {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
    if (withAnimation) {
        ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
                .setDuration(300)
                .start()
    }
}

fun View.gone(withAnimation: Boolean = false) {
    if (this.visibility != View.GONE) this.visibility = View.GONE
    if (withAnimation) {
        ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
                .setDuration(300)
                .start()
    }
}

fun View.invisible() {
    if (this.visibility != View.INVISIBLE) this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}