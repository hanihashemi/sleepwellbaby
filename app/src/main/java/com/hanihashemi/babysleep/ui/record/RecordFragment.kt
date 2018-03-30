package com.hanihashemi.babysleep.ui.record

import android.media.MediaRecorder
import android.support.v4.content.ContextCompat
import com.hanihashemi.babysleep.R
import com.hanihashemi.babysleep.base.BaseFragment
import kotlinx.android.synthetic.main.record_fragment.*
import timber.log.Timber
import java.io.File
import java.io.IOException

/**
 * Created by Hani on 3/26/18.
 */
class RecordFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.record_fragment
    private var mediaRecorder: MediaRecorder? = null
    private var file: File? = null

    override fun customizeUI() {
        btnCancel.setOnClickListener {
            deleteRecordingFile()
            activity.finish()
        }
        btnSave.setOnClickListener { activity.finish() }
    }

    override fun onStart() {
        super.onStart()
        startRecording()
    }

    override fun onStop() {
        super.onStop()
        stopRecording()
    }

    private fun newFile(): File {
        var id = 0
        var file: File

        do {
            val fileName = "${ContextCompat.getDataDir(context).absolutePath}/audio-${id++}.aac"
            file = File(fileName)
        } while (file.exists())

        return file
    }

    private fun startRecording() {
        file = newFile()

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
    }

    private fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null
    }

    private fun deleteRecordingFile() {
        file?.deleteOnExit()
    }
}