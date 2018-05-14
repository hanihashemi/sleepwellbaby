package com.hanihashemi.sleepwellbaby.ui.main

import android.support.annotation.VisibleForTesting
import com.hanihashemi.sleepwellbaby.model.Music

class MusicManager{
    private val musics: MutableList<Music> = mutableListOf()
    val size : Int
        get() = musics.size

    fun isEmpty() : Boolean = musics.isEmpty()

    fun addAll(elements: Collection<Music>) = musics.addAll(elements)

    fun removeAfter(index: Int) = musics.removeAll { it.id >= index }

    fun resetActiveMusics(except: Music? = null) = musics.forEach { it.isActive = it == except }

    operator fun get(index: Int) = musics[index]

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun getMusics(): MutableList<Music>{
        return musics
    }
}