package com.hanihashemi.sleepwellbaby.ui.main

import android.content.Context
import android.content.Intent
import com.hanihashemi.sleepwellbaby.MediaPlayerService
import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment
import com.hanihashemi.sleepwellbaby.model.Music
import kotlinx.android.synthetic.main.main_fragment_footer.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainFragmentTest {

    @Test
    fun messageReceiver_onReceive_noneStatus(){
        val activity: BaseActivityWithSingleFragment =
                Robolectric.buildActivity(MainActivity::class.java)
                .create().start().get()

        val fragment = spy(activity.fragment) as MainFragment
        val seekBar = fragment.seekBar
        val txtTimer = fragment.txtTimer

        val millisUntilFinish = 999999L
        val duration = 10000
        val currentPosition = 200

        fragment.messageReceiver.onReceive(activity as Context, sampleIntent(
                MediaPlayerService.STATUS.NONE,
                Music(-1, "dummy"),
                millisUntilFinish,
                duration,
                currentPosition
        ))

        assertEquals(duration, seekBar.max)
        assertEquals(currentPosition, seekBar.progress)
        assertEquals("16:39", txtTimer.text)
    }

    private fun sampleIntent(status: MediaPlayerService.STATUS,
                             music: Music,
                             millisUntilFinish: Long,
                             duration: Int,
                             currentPosition: Int): Intent {
        val intent = Intent()
        intent.putExtra(MediaPlayerService.BROADCAST_ARG_STATUS, status)
        intent.putExtra(MediaPlayerService.BROADCAST_ARG_MUSIC, music)
        intent.putExtra(MediaPlayerService.BROADCAST_ARG_MILLIS_UNTIL_FINISHED, millisUntilFinish)
        intent.putExtra(MediaPlayerService.BROADCAST_ARG_DURATION, duration)
        intent.putExtra(MediaPlayerService.BROADCAST_ARG_CURRENT_POSITION, currentPosition)
        return intent
    }
}