package com.hanihashemi.babysleep.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by hani on 12/24/17.
 */
abstract class BaseFragment : Fragment() {
    private lateinit var view: View
    protected abstract val layoutResource: Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(layoutResource, container, false)
        if (arguments != null)
            gatherArguments(arguments)
        customizeUI()
        return view
    }

    override fun getView(): View {
        return view
    }

    protected abstract fun customizeUI()

    open protected fun gatherArguments(bundle: Bundle) {}
}