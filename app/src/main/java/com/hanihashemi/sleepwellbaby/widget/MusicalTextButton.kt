package com.hanihashemi.sleepwellbaby.widget

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.helper.dpToPx

/**
 * Created by irantalent on 12/30/17.
 */
class MusicalTextButton : FrameLayout {
    val text = TextView(context, null)

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setElevation()
        isClickable = true
        isFocusable = true
        layoutParams = ViewGroup.LayoutParams(context.dpToPx(70F), context.dpToPx(70F))

        initText()
    }

    private fun setElevation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            elevation = context.dpToPx(3F).toFloat()
    }

    private fun initText() {
        text.setPadding(context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F))
        text.rotationY = 180F
        text.setTypeface(null, Typeface.BOLD)
        text.gravity = Gravity.CENTER

        addView(text)
    }

    fun setText(text2: String) {
        text.text = text2
    }

    fun setTextColor(@ColorRes color: Int) {
        text.setTextColor(ContextCompat.getColor(context, color))
    }

    fun addLock() {
        val lockImage = ImageView(context, null)
        lockImage.setImageResource(R.drawable.ic_locked_song)
        addView(lockImage)
    }
}