package com.hanihashemi.babysleep.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.hanihashemi.babysleep.R


/**
 * Created by hani on 12/21/17.
 */

class MusicButton : ImageView {

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        this.setBackgroundResource(R.drawable.music_button_background)
        this.setImageResource(R.drawable.ic_airplanemode)
    }
}