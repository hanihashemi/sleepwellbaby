package com.hanihashemi.sleepwellbaby.helper

import android.content.Context
import android.support.v4.content.ContextCompat
import java.io.File

class AudioFileHelper(private val context: Context) {
    fun newFile() =
            File("${ContextCompat.getDataDir(context)?.absolutePath}/" +
                    "${System.currentTimeMillis() / 1000}" +
                    ".aac")

    fun list(): Array<File>? {
        val audioFile = ContextCompat.getDataDir(context)
        return if (audioFile != null)
            audioFile.listFiles { file -> file.path.endsWith(".aac") }
        else null
    }

    fun size(): Int {
        val files = list()
        return if (files == null) 0 else files.size
    }
}