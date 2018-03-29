package com.hanihashemi.babysleep.ui.record

import com.hanihashemi.babysleep.R
import com.hanihashemi.babysleep.base.BaseFragment


/**
 * Created by Hani on 3/26/18.
 */
class RecordFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.record_fragment
    override fun customizeUI() {
//        btnCancel.setOnClickListener { activity.finish() }
//        btnSave.setOnClickListener {}
    }

    private fun startRecording() {
//        Toast.makeText(context, "Start Recording", Toast.LENGTH_LONG).show()

        //        var mFileName = activity.dataDir.absolutePath
//        mFileName += "/audiorecordtest.3gp";
//        val mediaRecorder = MediaRecorder()
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
    }
}