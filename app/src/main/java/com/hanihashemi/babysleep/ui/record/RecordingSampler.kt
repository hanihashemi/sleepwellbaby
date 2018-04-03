package com.hanihashemi.babysleep.ui.record

import android.media.AudioRecord
import java.util.*
import com.tyorikan.voicerecordingvisualizer.VisualizerView
import android.media.MediaRecorder
import android.media.AudioFormat

class RecordingSampler() {
    private val sampleRate = 44100

    private var audioRecord: AudioRecord? = null
    private var isRecording: Boolean = false
    private var bufSize: Int = 0

    private var samplingInterval: Long = 100
    private var timer: Timer? = null

    private val visualizerViews = mutableListOf<VisualizerView?>()

    init {
        initAudioRecord()
    }

    /**
     * link to VisualizerView
     *
     * @param visualizerView [VisualizerView]
     */
    fun link(visualizerView: VisualizerView) {
        visualizerViews.add(visualizerView)
    }

    /**
     * setter of samplingInterval
     *
     * @param samplingInterval interval volume sampling
     */
    fun setSamplingInterval(samplingInterval: Long) {
        this.samplingInterval = samplingInterval
    }

    /**
     * getter isRecording
     *
     * @return true:recording, false:not recording
     */
    fun isRecording(): Boolean {
        return isRecording
    }

    private fun initAudioRecord() {
        val bufferSize = AudioRecord.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
        )

        audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
        )

        if (audioRecord?.state == AudioRecord.STATE_INITIALIZED) {
            bufSize = bufferSize
        }
    }

    /**
     * start AudioRecord.read
     */
    fun startRecording() {
        timer = Timer()
        audioRecord?.startRecording()
        isRecording = true
        runRecording()
    }

    /**
     * stop AudioRecord.read
     */
    fun stopRecording() {
        isRecording = false
        timer?.cancel()

        if (!visualizerViews.isEmpty()) {
            for (i in 0 until visualizerViews.size) {
                visualizerViews[i].receive(0)
            }
        }
    }

    private fun runRecording() {
        val buf = ByteArray(bufSize)
        timer?.schedule(object : TimerTask() {
            override fun run() {
                // stop recording
                if (!isRecording) {
                    audioRecord?.stop()
                    return
                }
                audioRecord?.read(buf, 0, bufSize)

                val decibel = calculateDecibel(buf)
                if (!visualizerViews.isEmpty()) {
                    for (i in 0 until visualizerViews.size) {
                        visualizerViews[i].receive(decibel)
                    }
                }
            }
        }, 0, samplingInterval)
    }

    private fun calculateDecibel(buf: ByteArray): Int {
        var sum = 0
        for (i in 0 until bufSize) {
            sum += Math.abs(buf[i].toInt())
        }
        // avg 10-50
        return sum / bufSize
    }

    /**
     * release member object
     */
    fun release() {
        stopRecording()
        audioRecord?.release()
        audioRecord = null
        timer = null
    }
}