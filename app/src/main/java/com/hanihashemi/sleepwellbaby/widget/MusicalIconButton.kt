package com.hanihashemi.sleepwellbaby.widget

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.helper.dpToPx


/**
 * Created by hani on 12/21/17.
 */

class MusicalIconButton : FrameLayout {
    val image = ImageView(context, null)

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

        initImage()
    }

    private fun setElevation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            elevation = context.dpToPx(3F).toFloat()
    }

    private fun initImage() {
        image.setPadding(context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F),
                context.dpToPx(15F))
        image.rotationY = 180F

        addView(image)
    }

    fun setImageResource(@DrawableRes resId: Int) = image.setImageResource(resId)

    fun addLock() {
        val lockImage = ImageView(context, null)
        lockImage.setImageResource(R.drawable.ic_locked_song)
        addView(lockImage)
    }
}