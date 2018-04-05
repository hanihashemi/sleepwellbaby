package com.hanihashemi.sleepwellbaby

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by hani on 12/21/17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        NotificationManager(this as Context).createMainNotificationChannel()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG)
                return


        }
    }
}