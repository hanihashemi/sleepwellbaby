package com.hanihashemi.sleepwellbaby.helper

import org.junit.Test

import org.junit.Assert.*

class TimeHelperTest {

    @Test
    fun convertMillisToTime_underASecond_emptyResult() {
        assertEquals("", TimeHelper().convertMillisToTime(0))
        assertEquals("", TimeHelper().convertMillisToTime(10))
        assertEquals("", TimeHelper().convertMillisToTime(100))
    }

    @Test
    fun convertMillisToTime_allCases(){
        assertEquals("00:01", TimeHelper().convertMillisToTime(1000))
        assertEquals("00:10", TimeHelper().convertMillisToTime(10000))
        assertEquals("01:40", TimeHelper().convertMillisToTime(100000))
    }

}