package com.hanihashemi.babysleep

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import com.hanihashemi.babysleep.model.Music
import timber.log.Timber

/**
 * Created by irantalent on 1/4/18.
 */
class MediaPlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private lateinit var mediaPlayer: MediaPlayer
    private var music: Music? = null
    private var lastStatus = STATUS.STOP

    enum class ACTIONS {
        PLAY, PAUSE, STOP, SYNC
    }

    enum class STATUS {
        PLAYING, PAUSE, STOP
    }

    enum class ARGUMENTS {
        ACTION, MUSIC_OBJ
    }

    companion object {
        val BROADCAST_KEY = "message_from_mars"
        val BROADCAST_ARG_STATUS = "status"
        val BROADCAST_ARG_MUSIC = "music_track"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.getSerializableExtra(ARGUMENTS.ACTION.name)

            when ((action as ACTIONS)) {
                ACTIONS.PLAY -> {
                    val receivedMusic = intent.getParcelableExtra<Music>(ARGUMENTS.MUSIC_OBJ.name)
                    play(if (receivedMusic == null) this.music else receivedMusic)
                }
                ACTIONS.PAUSE -> pause()
                ACTIONS.STOP -> stop()
                ACTIONS.SYNC -> sync(lastStatus)
            }
        }

        return START_STICKY
    }

    private fun sync(status: STATUS) {
        lastStatus = status
        val intent = Intent(BROADCAST_KEY)
        intent.putExtra(BROADCAST_ARG_STATUS, lastStatus)
        intent.putExtra(BROADCAST_ARG_MUSIC, music)
        LocalBroadcastManager.getInstance(this as Context).sendBroadcast(intent)
    }

    private fun stop() {
        sync(STATUS.STOP)
        mediaPlayer.stop()
    }

    private fun pause() {
        sync(STATUS.PAUSE)
        mediaPlayer.pause()
    }

    private fun play(music: Music?) {
        if (music == null)
            return
        this.music = music
        sync(STATUS.PLAYING)
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/${music.fileId}"))
        mediaPlayer.isLooping = true
        mediaPlayer.prepareAsync()
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
    }

    override fun onDestroy() {
        sync(STATUS.STOP)
        mediaPlayer.release()
        super.onDestroy()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
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