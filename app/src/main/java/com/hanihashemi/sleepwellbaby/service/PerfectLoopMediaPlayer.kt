package com.hanihashemi.sleepwellbaby.service

/**
 * Created by irantalent on 1/18/18.
 */

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import java.io.IOException

class PerfectLoopMediaPlayer {
    private var mContext: Context? = null
    private var mResId = 0
    private var mPath: String? = null

    private var mCurrentPlayer: MediaPlayer? = null
    private var mNextPlayer: MediaPlayer? = null


    private val onCompletionListener = MediaPlayer.OnCompletionListener { mediaPlayer ->
        mCurrentPlayer = mNextPlayer
        if (mPath == null)
            createNextMediaPlayerRaw()
        else
            createNextMediaPlayerPath()
        mediaPlayer.release()
    }


    val isPlaying: Boolean
        @Throws(IllegalStateException::class)
        get() = if (mCurrentPlayer != null) {
            mCurrentPlayer!!.isPlaying
        } else {
            false
        }


    private constructor(context: Context, resId: Int) {
        mContext = context
        mResId = resId
        try {
            val afd = context.resources.openRawResourceFd(mResId)
            mCurrentPlayer = MediaPlayer()
            mCurrentPlayer!!.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mCurrentPlayer!!.setOnPreparedListener { mCurrentPlayer!!.start() }
            mCurrentPlayer!!.prepareAsync()
            createNextMediaPlayerRaw()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createNextMediaPlayerRaw() {
        val afd = mContext!!.resources.openRawResourceFd(mResId)
        mNextPlayer = MediaPlayer()
        try {
            mNextPlayer!!.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mNextPlayer!!.setOnPreparedListener {
                mNextPlayer!!.seekTo(0)
                mCurrentPlayer!!.setNextMediaPlayer(mNextPlayer)
                mCurrentPlayer!!.setOnCompletionListener(onCompletionListener)
            }
            mNextPlayer!!.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private constructor(context: Context, path: String) {
        mContext = context
        mPath = path
        try {
            mCurrentPlayer = MediaPlayer()
            mCurrentPlayer!!.setDataSource(mPath)
            mCurrentPlayer!!.setOnPreparedListener { mCurrentPlayer!!.start() }
            mCurrentPlayer!!.prepareAsync()
            createNextMediaPlayerPath()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createNextMediaPlayerPath() {
        mNextPlayer = MediaPlayer()
        try {
            mNextPlayer!!.setDataSource(mPath)
            mNextPlayer!!.setOnPreparedListener {
                mNextPlayer!!.seekTo(0)
                mCurrentPlayer!!.setNextMediaPlayer(mNextPlayer)
                mCurrentPlayer!!.setOnCompletionListener(onCompletionListener)
            }
            mNextPlayer!!.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IllegalStateException::class)
    fun start() {
        if (mCurrentPlayer != null) {
            Log.d(TAG, "start()")
            mCurrentPlayer!!.start()
        } else {
            Log.d(TAG, "start() | mCurrentPlayer is NULL")
        }

    }

    fun currentPosition(): Int? {
        return mCurrentPlayer?.currentPosition
    }

    fun duration(): Int? {
        return mCurrentPlayer?.duration
    }

    fun seekTo(msec: Int) {
        mCurrentPlayer?.seekTo(msec)
    }

    @Throws(IllegalStateException::class)
    fun stop() {
        if (mCurrentPlayer != null && mCurrentPlayer!!.isPlaying) {
            Log.d(TAG, "stop()")
            mCurrentPlayer!!.stop()
        } else {
            Log.d(TAG, "stop() | mCurrentPlayer " + "is NULL or not playing")
        }
    }

    @Throws(IllegalStateException::class)
    fun pause() {
        if (mCurrentPlayer != null && mCurrentPlayer!!.isPlaying) {
            Log.d(TAG, "pause()")
            mCurrentPlayer!!.pause()
        } else {
            Log.d(TAG, "pause() | mCurrentPlayer " + "is NULL or not playing")
        }

    }

    fun release() {
        Log.d(TAG, "release()")
        if (mCurrentPlayer != null)
            mCurrentPlayer!!.release()
        if (mNextPlayer != null)
            mNextPlayer!!.release()
    }

    companion object {


        private val TAG = PerfectLoopMediaPlayer::class.java.name

        /**
         * Creating instance of the player with given context and raw resource
         *
         * @param context - context
         * @param resId   - raw resource
         * @return new instance
         */
        fun create(context: Context, resId: Int): PerfectLoopMediaPlayer {
            return PerfectLoopMediaPlayer(context, resId)
        }

        /**
         * Creating instance of the player with given context
         * and internal memory/SD path resource
         *
         * @param context - context
         * @param path    - internal memory/SD path to sound resource
         * @return new instance
         */
        fun create(context: Context, path: String): PerfectLoopMediaPlayer {
            return PerfectLoopMediaPlayer(context, path)
        }
    }
}