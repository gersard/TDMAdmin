package cl.gerardomascayano.tdmadmin.core.ui

import android.content.Context

object ScreenSize {

    fun get(context: Context): Pair<Int, Int> {
        val displayMetrics = context.resources.displayMetrics
        return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }

}