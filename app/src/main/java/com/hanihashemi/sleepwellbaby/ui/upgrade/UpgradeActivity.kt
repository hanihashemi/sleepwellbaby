package com.hanihashemi.sleepwellbaby.ui.upgrade

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment

/**
 * Created by Hani on 4/7/18.
 */
class UpgradeActivity : BaseActivityWithSingleFragment() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, UpgradeActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun createFragment(): Fragment {
        return UpgradeFragment()
    }
}