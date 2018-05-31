package com.hanihashemi.sleepwellbaby.ui.main

import com.hanihashemi.sleepwellbaby.base.BaseActivityWithSingleFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainFragmentRobolectricTest {
    @Test
    fun updateVoiceFiles() {
        val activity = startActivity()

        val fragment = spy(activity.fragment as MainFragment)

        assertEquals(21, fragment.musicManager.size)

        fragment.updateVoiceFiles()
    }

    private fun startActivity(): BaseActivityWithSingleFragment {
        return Robolectric.buildActivity(MainActivity::class.java)
                .create().start().get()
    }
}