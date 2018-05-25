package com.hanihashemi.sleepwellbaby.base

import android.content.Intent
import android.support.v4.app.Fragment
import com.hanihashemi.sleepwellbaby.R

/**
 * Created by hani on 12/24/17.
 */
abstract class BaseActivityWithSingleFragment : BaseActivity() {
    private var fragmentTag: String? = null

    override val layoutResource
        get() = R.layout.activity_with_single_fragment

    val fragment: Fragment
        get() = supportFragmentManager.findFragmentByTag(fragmentTag)

    protected abstract fun createFragment(): Fragment

    override fun customizeUI() {
        val fragment = createFragment()
        fragmentTag = fragment.javaClass.simpleName

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTag)
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment.onActivityResult(requestCode, resultCode, data)
    }
}