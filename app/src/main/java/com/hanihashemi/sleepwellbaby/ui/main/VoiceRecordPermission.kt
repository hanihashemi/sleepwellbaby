package com.hanihashemi.sleepwellbaby.ui.main

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.widget.Toast
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
class VoiceRecordPermission(private val activity: Activity, private val record: () -> Unit) {
    fun check() {
        if (!isExternalStorageWritable()) {
            Toast.makeText(activity, "حافظه خارجی قابل نوشتن نیست!", Toast.LENGTH_LONG).show()
            return
        }

        if (hasPermission())
            record()
        else
            requestPermission()
    }

    private fun requestOnlyAudioPermission() {
        val dialogPermissionListener = DialogOnDeniedPermissionListener.Builder
                .withContext(activity)
                .withTitle("دسترسی ضبط صدا")
                .withMessage("برای ضبط صدای شما ما احتیاج به داشتن درسترسی ضبط صدا داریم.")
                .withButtonText(android.R.string.ok)
                .build()

        val basePermissionListener = object : BasePermissionListener() {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                record()
            }
        }

        val compositePermissionListener = CompositePermissionListener(dialogPermissionListener, basePermissionListener)

        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(compositePermissionListener).check()
    }

    private fun requestAudioAndStoragePermissions() {
        val dialogMultiplePermissionsListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(activity)
                        .withTitle("دسترسی ضبط صدا و حافظه")
                        .withMessage("برای ضبط صدای شما ما احتیاج به داشتن دسترسی به ضبط صدا و حافظه داریم.")
                        .withButtonText(android.R.string.ok)
                        .build()

        val baseMultiplePermissionsListener = object : BaseMultiplePermissionsListener() {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report != null && report.areAllPermissionsGranted())
                    record()
            }
        }

        val compositeListener = CompositeMultiplePermissionsListener(dialogMultiplePermissionsListener, baseMultiplePermissionsListener)

        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                ).withListener(compositeListener).check()
    }

    private fun requestPermission() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)
            requestAudioAndStoragePermissions()
        else
            requestOnlyAudioPermission()
    }

    private fun hasPermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val hasAudioPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
            val hasWritePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

            hasAudioPermission && hasWritePermission
        } else
            ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
}