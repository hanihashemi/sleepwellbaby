package com.hanihashemi.sleepwellbaby.ui.main

import android.support.v4.app.Fragment
import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment
import com.hanihashemi.sleepwellbaby.helper.AudioFileHelper
import javax.inject.Inject

class MainActivity : BaseActivityWithSingleFragment() {
    @Inject
    lateinit var audioFileHelper: AudioFileHelper

    override fun createFragment(): Fragment {
        audioFileHelper.newFile(this)
        return MainFragment()
    }
}