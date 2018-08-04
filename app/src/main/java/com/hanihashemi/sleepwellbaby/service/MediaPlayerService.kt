package com.hanihashemi.sleepwellbaby.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Handler
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import com.hanihashemi.sleepwellbaby.notification.NotificationManager
import com.hanihashemi.sleepwellbaby.PerfectLoopMediaPlayer
import com.hanihashemi.sleepwellbaby.model.Music
import timber.log.Timber

/**
 * Created by irantalent on 1/4/18.
 */
class MediaPlayerService : Service(), MediaPlayer.OnErrorListener {
    private var player: PerfectLoopMediaPlayer? = null
    private var music: Music? = null
    private var lastStatus = STATUS.STOP
    private var sleepTimerMillis = 0L
    private var countDownTimer: CountDownTimer? = null
    private val progressHandler = Handler()

    enum class ACTIONS {
        PLAY, PAUSE, STOP, SYNC, SET_SLEEP_TIMER, SEEK_TO,
    }

    enum class STATUS {
        PLAYING, PAUSE, STOP, NONE
    }

    enum class ARGUMENTS {
        ACTION, MUSIC_OBJ, SLEEP_TIMER_MILLIS, SEEK_TO_MILLIS,
    }

    companion object {
        const val BROADCAST_KEY = "message_from_mars"
        const val BROADCAST_ARG_STATUS = "status"
        const val BROADCAST_ARG_MUSIC = "music_track"
        const val BROADCAST_ARG_MILLIS_UNTIL_FINISHED = "current_millis"
        const val BROADCAST_ARG_DURATION = "duration"
        const val BROADCAST_ARG_CURRENT_POSITION = "current_position"
        const val ONGOING_NOTIFICATION_ID = 221
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.getSerializableExtra(ARGUMENTS.ACTION.name)

            when ((action as ACTIONS)) {
                ACTIONS.PLAY -> {
                    val receivedMusic = intent.getParcelableExtra<Music>(ARGUMENTS.MUSIC_OBJ.name)
                    play(receivedMusic ?: this.music)
                }
                ACTIONS.PAUSE -> pause()
                ACTIONS.STOP -> stop()
                ACTIONS.SYNC -> sync(lastStatus)
                ACTIONS.SEEK_TO -> {
                    seekTo(intent.getIntExtra(ARGUMENTS.SEEK_TO_MILLIS.name, 0))
                }
                ACTIONS.SET_SLEEP_TIMER -> {
                    sleepTimerMillis = intent.getLongExtra(ARGUMENTS.SLEEP_TIMER_MILLIS.name, 0)
                    stopTimer()
                    if (player?.isPlaying!!)
                        startTimer()
                }
            }
        }

        return START_STICKY
    }

    private fun seekTo(toMillis: Int) {
        player?.seekTo(toMillis)
    }

    private fun startTimer() {
        if (sleepTimerMillis <= 2000L) {
            sleepTimerMillis = 0
            return
        }
        this.countDownTimer = object : CountDownTimer(sleepTimerMillis, 1000) {
            override fun onFinish() {
                stop()
            }

            override fun onTick(millisUntilFinished: Long) {
                sleepTimerMillis = millisUntilFinished
                sync(STATUS.PLAYING)
            }
        }
        this.countDownTimer?.start()
    }

    private fun stopTimer() {
        this.countDownTimer?.cancel()
    }

    private fun sync(status: STATUS) {
        lastStatus = status
        val intent = Intent(BROADCAST_KEY)
        intent.putExtra(BROADCAST_ARG_STATUS, lastStatus)
        intent.putExtra(BROADCAST_ARG_MUSIC, music)
        intent.putExtra(BROADCAST_ARG_MILLIS_UNTIL_FINISHED, sleepTimerMillis + 1)
        intent.putExtra(BROADCAST_ARG_DURATION, player?.duration())
        intent.putExtra(BROADCAST_ARG_CURRENT_POSITION, player?.currentPosition())
        LocalBroadcastManager.getInstance(this as Context).sendBroadcast(intent)
    }

    private fun stop() {
        sync(STATUS.STOP)
        player?.stop()
        stopTimer()
        stopForeground(true)
    }

    private fun pause() {
        sync(STATUS.PAUSE)
        stopTimer()
        player?.pause()
        stopForeground(true)
    }

    private fun play(music: Music?) {
        if (music == null)
            return

//        var lastPosition = player?.currentPosition() ?: 0

        if (player != null && player!!.isPlaying) {
            player?.stop()
            player?.release()
        }

//        if (this.music?.fileId != music.fileId)
//            lastPosition = 0

        this.music = music
        startTimer()

        if (music.file != null)
            player = PerfectLoopMediaPlayer.create(this, music.file!!.absolutePath)
        else
            player = PerfectLoopMediaPlayer.create(this, music.fileId)

        showNotification("در حال پخش")
        sync(STATUS.PLAYING)

        progressHandler.removeCallbacksAndMessages(null)
        progressHandler.post(object : Runnable {
            override fun run() {
                sync(lastStatus)
                if (lastStatus == STATUS.PLAYING)
                    progressHandler.postDelayed(this, 1000)
            }

        })
    }

    private fun showNotification(title: String) = startForeground(ONGOING_NOTIFICATION_ID, NotificationManager(this as Context).mediaPlayerServiceNotification(title))

    override fun onDestroy() {
        stop()
        super.onDestroy()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        sync(STATUS.STOP)

        when (what) {
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> Timber.w("Error =====> MEDIA_ERROR_UNKNOWN")
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> Timber.w("Error =====> MEDIA_ERROR_SERVER_DIED")
        }

        when (extra) {
            MediaPlayer.MEDIA_ERROR_IO -> Timber.w("Error =====> MEDIA_ERROR_IO")
            MediaPlayer.MEDIA_ERROR_MALFORMED -> Timber.w("Error =====> MEDIA_ERROR_MALFORMED")
            MediaPlayer.MEDIA_ERROR_TIMED_OUT -> Timber.w("Error =====> MEDIA_ERROR_TIME_OUT")
            MediaPlayer.MEDIA_ERROR_UNSUPPORTED -> Timber.w("Error =====> MEDIA_ERROR_UNSUPPORTED")
        }

        return true
    }
}