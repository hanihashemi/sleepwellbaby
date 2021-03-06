package com.hanihashemi.sleepwellbaby.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import com.hanihashemi.sleepwellbaby.service.MediaPlayerService
import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment
import com.hanihashemi.sleepwellbaby.model.Music
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment_footer.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainFragmentTest_MessageReceiver {

    @Test
    fun messageReceiver_onReceive_noneStatus() {
        val activity = startActivity()

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

    @Test
    fun messageReceiver_onReceive_playingStatus() {
        val activity = startActivity()

        val fragment = spy(activity.fragment) as MainFragment
        val seekBar = fragment.seekBar
        val txtTimer = fragment.txtTimer
        val controlLayout = fragment.includeControlLayout

        val millisUntilFinish = 999999L
        val duration = 10000
        val currentPosition = 200
        val music = Music(-1, "dummy")

        fragment.messageReceiver.onReceive(activity as Context, sampleIntent(
                MediaPlayerService.STATUS.PLAYING,
                music,
                millisUntilFinish,
                duration,
                currentPosition
        ))

//        verify(fragment).notifyAllAdapters()
        assertEquals(View.VISIBLE, controlLayout.visibility)
        assertEquals(View.VISIBLE, seekBar.visibility)
        assertEquals(duration, seekBar.max)
        assertEquals(currentPosition, seekBar.progress)
        assertEquals("16:39", txtTimer.text)
    }

    @Test
    fun messageReceiver_onReceive_pauseStatus() {
        val activity = startActivity()

        val fragment = spy(activity.fragment) as MainFragment
        val seekBar = fragment.seekBar
        val txtTimer = fragment.txtTimer
        val controlLayout = fragment.includeControlLayout

        val millisUntilFinish = 999999L
        val duration = 10000
        val currentPosition = 200
        val music = Music(-1, "dummy")

        fragment.messageReceiver.onReceive(activity as Context, sampleIntent(
                MediaPlayerService.STATUS.PAUSE,
                music,
                millisUntilFinish,
                duration,
                currentPosition
        ))

//        verify(fragment).notifyAllAdapters()
        assertEquals(View.VISIBLE, controlLayout.visibility)
        assertEquals(View.VISIBLE, seekBar.visibility)
        assertEquals(duration, seekBar.max)
        assertEquals(currentPosition, seekBar.progress)
        assertEquals("16:39", txtTimer.text)
    }

    @Test
    fun messageReceiver_onReceive_stopStatus() {
        val activity = startActivity()

        val fragment = spy(activity.fragment) as MainFragment
        val seekBar = fragment.seekBar
        val txtTimer = fragment.txtTimer
        val controlLayout = fragment.includeControlLayout

        val millisUntilFinish = 999999L
        val duration = 10000
        val currentPosition = 200
        val music = Music(-1, "dummy")

        fragment.messageReceiver.onReceive(activity as Context, sampleIntent(
                MediaPlayerService.STATUS.STOP,
                music,
                millisUntilFinish,
                duration,
                currentPosition
        ))

//        verify(fragment).notifyAllAdapters()
        assertEquals(View.GONE, controlLayout.visibility)
        assertEquals(View.GONE, seekBar.visibility)
        assertEquals("16:39", txtTimer.text)
    }

    private fun startActivity(): BaseActivityWithSingleFragment {
        return Robolectric.buildActivity(MainActivity::class.java)
                .create().start().get()
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