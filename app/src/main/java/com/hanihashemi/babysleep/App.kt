package com.hanihashemi.babysleep

import android.app.Application
import android.support.v7.app.AppCompatDelegate

/**
 * Created by hani on 12/21/17.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}