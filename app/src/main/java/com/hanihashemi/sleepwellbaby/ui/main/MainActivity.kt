package com.hanihashemi.sleepwellbaby.ui.main

import android.support.v4.app.Fragment
import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment

class MainActivity : BaseActivityWithSingleFragment() {

    override fun createFragment(): Fragment {
        return MainFragment()
    }
}