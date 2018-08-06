package com.hanihashemi.sleepwellbaby.ui.upgrade

import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.base.BaseFragment
import com.hanihashemi.sleepwellbaby.helper.IntentHelper
import kotlinx.android.synthetic.main.upgrade_fragment.*

/**
 * Created by Hani on 4/7/18.
 */
class UpgradeFragment : BaseFragment(){
    override val layoutResource: Int get() = R.layout.upgrade_fragment

    override fun customizeUI() {
        close.setOnClickListener { activity!!.finish() }
        upgrade.setOnClickListener { IntentHelper().openMarket(activity!!, "com.hanihashemi.sleepwellbaby") }
    }
}