package com.hanihashemi.babysleep

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

/**
 * Created by irantalent on 1/13/18.
 */
class NotificationManager(private val context: Context) {

    companion object {
        private val CHANNEL_ID = "YOUR_CHANNEL_ID"
        private val CHANNEL_NAME = "Your human readable notification channel name"
        private val CHANNEL_DESCRIPTION = "description"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMainNotificationId(): String {
        return CHANNEL_ID
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun createMainNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = CHANNEL_ID
            val name = CHANNEL_NAME
            val description = CHANNEL_DESCRIPTION
            val importance = android.app.NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(id, name, importance)
            mChannel.description = description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }

    @Suppress("DEPRECATION")
    private fun createNotification(): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            NotificationCompat.Builder(context, getMainNotificationId())
        else
            NotificationCompat.Builder(context)
    }

    fun mediaPlayerServiceNotification(title: String): Notification {
        val mainActivityIntent = mainActivityPendingIntent()
        val pendingIntentAction = mediaPlayerServiceStopPendingIntent()
        val action = NotificationCompat.Action.Builder(R.drawable.abc_btn_check_material, "بستن", pendingIntentAction).build()

        return NotificationManager(context).createNotification()
                .addAction(action)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(mainActivityIntent)
                .build()
    }

    private fun mediaPlayerServiceStopPendingIntent(): PendingIntent? {
        val intent = Intent(context, MediaPlayerService::class.java)
        intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.STOP)
        return PendingIntent.getService(context, -1, intent, FLAG_ONE_SHOT)
    }

    private fun mainActivityPendingIntent(): PendingIntent? {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, -1, intent, 0)
    }
}