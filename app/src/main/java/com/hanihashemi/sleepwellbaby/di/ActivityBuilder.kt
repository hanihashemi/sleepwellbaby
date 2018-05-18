package com.hanihashemi.sleepwellbaby.di

import com.hanihashemi.sleepwellbaby.ui.main.MainActivity
import com.hanihashemi.sleepwellbaby.ui.main.MainActivityModule
import com.hanihashemi.sleepwellbaby.ui.record.RecordActivity
import com.hanihashemi.sleepwellbaby.ui.record.RecordActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(RecordActivityModule::class)])
    abstract fun bindDetailActivity(): RecordActivity
}