package com.hanihashemi.sleepwellbaby.ui.main

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.hanihashemi.sleepwellbaby.ui.record.RecordActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.single.BasePermissionListener
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener

/**
 * Created by Hani on 3/30/18.
 */
class VoiceRecordPermission(private val context: Activity) {
    fun check(activity: Activity) {
        if (!isExternalStorageWritable()) {
            Toast.makeText(context, "حافظه خارجی قابل نوشتن نیست!", Toast.LENGTH_LONG).show()
            return
        }

        if (hasPermission())
            startRecording()
        else
            requestPermission(activity)
    }

    private fun requestOnlyAudioPermission(activity: Activity) {
        val dialogPermissionListener = DialogOnDeniedPermissionListener.Builder
                .withContext(context)
                .withTitle("دسترسی ضبط صدا")
                .withMessage("برای ضبط صدای شما ما احتیاج به داشتن درسترسی ضبط صدا داریم.")
                .withButtonText(android.R.string.ok)
                .build()

        val basePermissionListener = object : BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                startRecording()
            }
        }

        val compositePermissionListener = CompositePermissionListener(dialogPermissionListener, basePermissionListener)

        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(compositePermissionListener).check()
    }

    private fun requestAudioAndStoragePermissions(activity: Activity) {
        val dialogMultiplePermissionsListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(context)
                        .withTitle("دسترسی ضبط صدا و حافظه")
                        .withMessage("برای ضبط صدای شما ما احتیاج به داشتن دسترسی به ضبط صدا و حافظه داریم.")
                        .withButtonText(android.R.string.ok)
                        .build()

        val baseMultiplePermissionsListener = object : BaseMultiplePermissionsListener() {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report != null && report.areAllPermissionsGranted())
                    startRecording()
            }
        }

        val compositeListener = CompositeMultiplePermissionsListener(dialogMultiplePermissionsListener, baseMultiplePermissionsListener)

        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                ).withListener(compositeListener).check()
    }

    private fun requestPermission(activity: Activity) {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
            requestAudioAndStoragePermissions(activity)
        else
            requestOnlyAudioPermission(activity)
    }

    private fun hasPermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val hasAudioPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
            val hasWritePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            hasAudioPermission && hasWritePermission
        } else
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun startRecording() {
        RecordActivity.start(context)
    }
}