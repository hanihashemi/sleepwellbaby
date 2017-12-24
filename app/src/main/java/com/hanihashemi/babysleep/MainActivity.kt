package com.hanihashemi.babysleep

import android.support.v4.app.Fragment
import com.hanihashemi.babysleep.base.BaseActivityWithSingleFragment

class MainActivity : BaseActivityWithSingleFragment() {
    override fun createFragment(): Fragment {
        return MainFragment()
    }
}