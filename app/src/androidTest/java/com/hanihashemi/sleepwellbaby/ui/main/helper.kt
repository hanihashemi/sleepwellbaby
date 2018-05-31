package com.hanihashemi.sleepwellbaby.ui.main

fun sleep(duration: Long) {
    try {
        Thread.sleep(duration)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}