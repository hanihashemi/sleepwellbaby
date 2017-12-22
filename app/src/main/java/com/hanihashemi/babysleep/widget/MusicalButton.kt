package com.hanihashemi.babysleep.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import com.hanihashemi.babysleep.R
import com.hanihashemi.babysleep.helper.dpToPx


/**
 * Created by hani on 12/21/17.
 */

class MusicalButton : ImageView {

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        setBackgroundResource(R.drawable.music_button_background)
        setImageResource(R.drawable.ic_cleaner)
        setPadding(context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F))
        setElevation()
        isClickable = true
        isFocusable = true
    }

    private fun setElevation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = context.dpToPx(3F).toFloat()
        }
    }
}