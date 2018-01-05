package com.hanihashemi.babysleep

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder


/**
 * Created by irantalent on 1/4/18.
 */
public class MediaPlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    lateinit var mediaPlayer: MediaPlayer
    var count = 0

    public enum class ACTION {
        PLAY, PAUSE, STOP, SYNC
    }

    public enum class ARGUMENT {
        ACTION, SONG_NAME
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
                    val songId = intent.getIntExtra(ARGUMENT.SONG_NAME.name, -1)
                    if (songId == -1)
                        throw Exception("songId doesn't provided !!")
                    else
                        play(songId)
                }
                ACTION.PAUSE.ordinal -> {
                    pause()
                }
                ACTION.STOP.ordinal -> {
                    stop()
                }
                ACTION.SYNC.ordinal -> {

                }
            }
        }

        return START_STICKY
    }

    private fun stop() {
        mediaPlayer.stop()
        mediaPlayer.release()
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
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.isLooping = true
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCompletion(mp: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}