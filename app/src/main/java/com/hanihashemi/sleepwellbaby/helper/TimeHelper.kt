package com.hanihashemi.sleepwellbaby.helper

class TimeHelper {
    fun convertMillisToTime(millisUntilFinished: Long?): String {
        val second = millisUntilFinished!! / 1000 % 60
        val minute = millisUntilFinished / (1000 * 60) % 60

        val time = String.format("%02d:%02d", minute, second)
        return if (time == "00:00") "" else time
    }
}