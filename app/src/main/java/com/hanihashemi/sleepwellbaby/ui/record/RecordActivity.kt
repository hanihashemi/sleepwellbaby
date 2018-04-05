package com.hanihashemi.sleepwellbaby.ui.record

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment

/**
 * Created by Hani on 3/26/18.
 */
class RecordActivity : BaseActivityWithSingleFragment() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, RecordActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun createFragment(): Fragment {
        return RecordFragment()
    }
}