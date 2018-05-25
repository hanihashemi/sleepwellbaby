package com.hanihashemi.sleepwellbaby.ui.record

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment

/**
 * Created by Hani on 3/26/18.
 */
class RecordActivity : BaseActivityWithSingleFragment() {
    companion object {
        const val requestCode = 222

        fun start(activity: Activity) {
            val starter = Intent(activity, RecordActivity::class.java)
            activity.startActivityForResult(starter, requestCode)
        }
    }

    override fun createFragment(): Fragment {
        return RecordFragment()
    }
}