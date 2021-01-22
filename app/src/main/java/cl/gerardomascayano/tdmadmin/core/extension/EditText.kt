package cl.gerardomascayano.tdmadmin.core.extension

import android.app.Service
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    this.clearFocus()
    val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}