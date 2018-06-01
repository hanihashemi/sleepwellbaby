package com.hanihashemi.sleepwellbaby.helper

import android.content.Context
import android.support.v4.content.ContextCompat
import java.io.File

class AudioFileHelper {
    fun newFile(context: Context) =
            File("${ContextCompat.getDataDir(context)?.absolutePath}/" +
                    "${System.currentTimeMillis() / 1000}" +
                    ".aac")

    fun list(context: Context): Array<File>? {
        val audioFile = ContextCompat.getDataDir(context)
        return if (audioFile != null)
            audioFile.listFiles { file -> file.path.endsWith(".aac") }
        else null
    }
}