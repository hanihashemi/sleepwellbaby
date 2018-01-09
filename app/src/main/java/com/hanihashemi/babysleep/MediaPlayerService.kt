package com.hanihashemi.babysleep

import android.app.ActivityManager
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
    lateinit var mediaPlayer: MediaPlayer

    enum class ACTION {
        PLAY, PAUSE, STOP, SYNC
    }

    enum class ARGUMENT {
        ACTION, MUSIC_OBJ
    }


    companion object {
        fun isServiceRunning(context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            return manager.getRunningServices(Integer.MAX_VALUE).any { MediaPlayerService::class.java.name == it.service.className }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.getIntExtra(ARGUMENT.ACTION.name, ACTION.SYNC.ordinal)

            when (action) {
                ACTION.PLAY.ordinal -> {
                    val music = intent.getParcelableExtra<Music>(ARGUMENT.MUSIC_OBJ.name)
                    play(music.fileId)
                }
                ACTION.PAUSE.ordinal -> {
                    pause()
                }
                ACTION.STOP.ordinal -> {
                    stop()
                }
            }

            sync()
        }

        return START_STICKY
    }

    private fun sync() {
        val intent = Intent("custom-event-name")
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun stop() {
        mediaPlayer.stop()
    }

    private fun pause() {
        mediaPlayer.pause()
    }

    private fun play(songId: Int) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/$songId"))
        mediaPlayer.prepareAsync()
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null


    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when (what){
            MediaPlayer.MEDIA_ERROR_UNKNOWN -> Timber.w("Error =====> MEDIA_ERROR_UNKNOWN")
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> Timber.w("Error =====> MEDIA_ERROR_SERVER_DIED")
        }

        when (extra){
            MediaPlayer.MEDIA_ERROR_IO -> Timber.w("Error =====> MEDIA_ERROR_IO")
            MediaPlayer.MEDIA_ERROR_MALFORMED -> Timber.w("Error =====> MEDIA_ERROR_MALFORMED")
            MediaPlayer.MEDIA_ERROR_TIMED_OUT -> Timber.w("Error =====> MEDIA_ERROR_TIME_OUT")
            MediaPlayer.MEDIA_ERROR_UNSUPPORTED -> Timber.w("Error =====> MEDIA_ERROR_UNSUPPORTED")
        }

        return true
    }
}