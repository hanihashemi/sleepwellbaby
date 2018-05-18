package com.hanihashemi.sleepwellbaby.ui.record

import android.graphics.Color
import android.media.MediaRecorder
import android.os.Handler
import android.support.v4.content.ContextCompat
import com.gelitenight.waveview.library.WaveView
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.base.BaseFragment
import com.hanihashemi.sleepwellbaby.helper.AudioFileHelper
import kotlinx.android.synthetic.main.record_fragment.*
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Created by Hani on 3/26/18.
 */
class RecordFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.record_fragment
    private var mediaRecorder: MediaRecorder? = null
    private var file: File? = null
    private val timer = Timer()
    private var timerMillis = 0L
    private lateinit var waveHelper: WaveHelper

    override fun customizeUI() {
        btnCancel.setOnClickListener {
            stopRecording(true)
            activity.finish()
        }
        btnSave.setOnClickListener { activity.finish() }

        waveHelper = WaveHelper(wave)
        wave.setShapeType(WaveView.ShapeType.SQUARE)
        wave.setBorder(0, Color.BLACK)
        wave.setWaveColor(
                ContextCompat.getColor(context, R.color.waveBehind),
                ContextCompat.getColor(context, R.color.waveFront))
    }

    override fun onStart() {
        super.onStart()
        startRecording()
    }

    override fun onStop() {
        stopRecording()
        super.onStop()
        if (!activity.isFinishing)
            activity.finish()
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            waveHelper.start()
        }, 2000)
    }

    override fun onPause() {
        waveHelper.cancel()
        super.onPause()
    }

    private fun startRecording() {
        file = AudioFileHelper().newFile(context)

        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
        mediaRecorder?.setOutputFile(file?.absolutePath)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

        try {
            mediaRecorder?.prepare()
        } catch (e: IOException) {
            Timber.d(e, "prepare() failed")
        }

        mediaRecorder?.start()

        timer.schedule(object : TimerTask() {
            override fun run() {
                if (timerMillis == 300L)
                    activity.finish()

                activity.runOnUiThread { txtTimer.text = convertMillisToTime(timerMillis * 1000) }
                timerMillis++
            }
        }, 0, 1000)
    }

    private fun stopRecording(delete: Boolean = false) {
        timer.cancel()
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null
        if (delete) file?.delete()
    }

    private fun convertMillisToTime(millis: Long?): String {
        val second = millis!! / 1000 % 60
        val minute = millis / (1000 * 60) % 60

        return String.format("%02d:%02d", minute, second)
    }
}