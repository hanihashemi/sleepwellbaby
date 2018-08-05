package com.hanihashemi.sleepwellbaby.widget

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.helper.dpToPx
import com.hanihashemi.sleepwellbaby.helper.isRTL

/**
 * Created by irantalent on 12/30/17.
 */
class MusicalTextButton : FrameLayout {
    val text = AppCompatTextView(context, null)

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
        TextViewCompat.setAutoSizeTextTypeWithDefaults(text, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        text.setPadding(context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F))
        text.rotationY = if (isRTL()) 180F else 0F
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