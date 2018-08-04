package com.hanihashemi.sleepwellbaby.ui.main

import android.content.Intent
import android.view.MenuItem
import com.hanihashemi.sleepwellbaby.service.MediaPlayerService
import com.hanihashemi.sleepwellbaby.R
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener

fun showBottomSheet(activity: MainActivity) {
    BottomSheet.Builder(activity)
            .setSheet(R.menu.timer)
            .setStyle(R.style.BottomSheet)
            .setTitle("زمانبندی توقف أهنگ")
            .setCancelable(true)
            .setListener(object : BottomSheetListener {
                override fun onSheetDismissed(p0: BottomSheet, p1: Any?, p2: Int) {

                }

                override fun onSheetShown(p0: BottomSheet, p1: Any?) {

                }

                override fun onSheetItemSelected(p0: BottomSheet, item: MenuItem?, p2: Any?) {
                    var millis = 0L
                    when (item?.itemId) {
                        R.id.fiveMinutes -> {
                            millis = 300000L
                        }
                        R.id.fifteenMinutes -> {
                            millis = 900000L
                        }
                        R.id.thirtyMinutes -> {
                            millis = 18000000L
                        }
                        R.id.oneHour -> {
                            millis = 3600000L
                        }
                    }

                    val intent = Intent(activity, MediaPlayerService::class.java)
                    intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.SET_SLEEP_TIMER)
                    intent.putExtra(MediaPlayerService.ARGUMENTS.SLEEP_TIMER_MILLIS.name, millis)
                    activity.startService(intent)
                }
            })
            .show()
}