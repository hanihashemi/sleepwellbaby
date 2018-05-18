package com.hanihashemi.sleepwellbaby

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.hanihashemi.sleepwellbaby.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasFragmentInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


@Suppress("unused")
/**
 * Created by hani on 12/21/17.
 */
class App : Application(), HasActivityInjector, HasFragmentInjector {
    @Inject
    var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
    @Inject
    var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        NotificationManager(this as Context).createMainNotificationChannel()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
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