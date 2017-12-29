package com.hanihashemi.babysleep.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ShareCompat
import android.widget.Toast

@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by irantalent on 12/29/17.
 */
class IntentHelper {
    fun noFound(context: Context) {
        Toast.makeText(context, "Nothing found to run this action", Toast.LENGTH_LONG).show()
    }

    fun start(activity: Activity, intent: Intent) {
        if (intent.resolveActivity(activity.packageManager) != null)
            activity.startActivity(intent)
        else
            noFound(activity)
    }

    fun openAppInfo(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        start(activity, intent)
    }

    fun openAirplaneModeSettings(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_AIRPLANE_MODE_SETTINGS
        start(activity, intent)
    }

    fun openMarket(context: Activity) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=" + context.packageName)
        start(context, intent)
    }

    fun openLink(activity: Activity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        start(activity, intent)
    }

    fun sendMail(activity: Activity, email: String, subject: String, text: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, text)
        activity.startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    fun share(activity: Activity, text: String) {
        ShareCompat.IntentBuilder
                .from(activity)
                .setText(text)
                .setType("text/plain")
                .setChooserTitle("Share it")
                .startChooser()
    }

    fun getAction(url: String): Intent {
        return getAction(Uri.parse(url))
    }

    fun getAction(uri: Uri): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        return intent
    }
}