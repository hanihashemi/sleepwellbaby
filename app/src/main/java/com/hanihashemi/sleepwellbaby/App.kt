package com.hanihashemi.sleepwellbaby

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber
import timber.log.Timber.DebugTree


@Suppress("unused")
/**
 * Created by hani on 12/21/17.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        DaggerAppComponent
//                .builder()
//                .application(this)
//                .build()
//                .inject(this)

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this)

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
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
                return

            if (t != null)
                Crashlytics.logException(t)
        }
    }
}