package com.hanihashemi.sleepwellbaby.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.Button
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.helper.dpToPx

/**
 * Created by irantalent on 12/30/17.
 */
class MusicalTextButton : Button{
    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        setPadding(context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F))
        setElevation()
        isClickable = true
        isFocusable = true
        rotationY = 180F
    }

    private fun setElevation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = context.dpToPx(3F).toFloat()
        }
    }
}