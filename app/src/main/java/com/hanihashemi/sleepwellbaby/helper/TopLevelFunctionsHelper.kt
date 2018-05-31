package com.hanihashemi.sleepwellbaby.helper

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.*

/**
 * Created by hani on 12/21/17.
 */

fun EditText.trim(): String = this.text.toString().trim()

fun Context.dpToPx(dps: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, resources.displayMetrics).toInt()

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun isRTL(): Boolean {
    val locale = Locale.getDefault()
    val directionality = Character.getDirectionality(locale.displayName[0]).toInt()
    return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
}