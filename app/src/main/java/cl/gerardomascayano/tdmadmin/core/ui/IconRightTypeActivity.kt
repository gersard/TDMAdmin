package cl.gerardomascayano.tdmadmin.core.ui

import androidx.annotation.DrawableRes
import cl.gerardomascayano.tdmadmin.R

enum class IconRightTypeActivity(@DrawableRes val drwRes: Int? = null) {
    NONE(), NOTE(R.drawable.ic_note)
}