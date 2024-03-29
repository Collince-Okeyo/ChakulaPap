package com.ramgdev.chakulapap.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    const val REQUEST_ID = 0
}