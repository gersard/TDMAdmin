package cl.gerardomascayano.tdmadmin.core.extension

import android.app.Activity
import android.app.Service
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Activity.showKeyboard() {
    val imm = getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let { imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT) }

}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        imm.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}