package com.hanihashemi.sleepwellbaby.helper

import android.content.Context
import android.support.v4.content.ContextCompat
import java.io.File

class FileHelper {
    fun newFile(context: Context) =
            File("${ContextCompat.getDataDir(context).absolutePath}/${System.currentTimeMillis() / 1000}.aac")
}