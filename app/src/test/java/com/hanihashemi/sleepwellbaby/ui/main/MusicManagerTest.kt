package com.hanihashemi.sleepwellbaby.ui.main

import com.hanihashemi.sleepwellbaby.model.Music
import org.junit.Assert.*
import org.junit.Test

class MusicManagerTest {
    @Test
    fun isEmpty_false_scenario() {
        val musicManager = MusicManager()
        musicManager.addAll(populate())
        assertFalse(musicManager.isEmpty())
    }

    @Test
    fun isEmpty_true_scenario(){
        val musicManager = MusicManager()
        assertTrue(musicManager.isEmpty())
    }

    @Test
    fun removeAfter(){
        val musicManager = MusicManager()
        musicManager.addAll(populate())
        assertEquals(23, musicManager.size)

        musicManager.removeAfter(20)
        assertEquals(20, musicManager.size)
        assertEquals(Music(id = 19, name = "Dummy"), musicManager[19])
    }

    @Test
    fun resetActiveMusics(){
        val musicManager = MusicManager()
        musicManager.addAll(populateActiveMusics())

        assertFalse(musicManager.resetActiveMusics().all { (it as Music).isActive})
    }

    private fun populateActiveMusics(): MutableList<Music>{
        val musics = mutableListOf<Music>()
        musics.add(Music(0, "Dummy", isActive = true))
        musics.add(Music(1, "Dummy", isActive = true))
        musics.add(Music(2, "Dummy", isActive = true))
        return musics
    }

    private fun populate(): MutableList<Music> {
        val musics = mutableListOf<Music>()
        musics.add(Music(id = 0, name = "Dummy"))
        musics.add(Music(id = 1, name = "Dummy"))
        musics.add(Music(id = 2, name = "Dummy"))
        musics.add(Music(id = 3, name = "Dummy"))
        musics.add(Music(id = 4, name = "Dummy"))
        musics.add(Music(id = 5, name = "Dummy"))
        musics.add(Music(id = 6, name = "Dummy"))
        musics.add(Music(id = 7, name = "Dummy"))
        musics.add(Music(id = 8, name = "Dummy"))
        musics.add(Music(id = 9, name = "Dummy"))
        musics.add(Music(id = 10, name = "Dummy"))
        musics.add(Music(id = 11, name = "Dummy"))
        musics.add(Music(id = 12, name = "Dummy"))
        musics.add(Music(id = 13, name = "Dummy"))
        musics.add(Music(id = 14, name = "Dummy"))
        musics.add(Music(id = 15, name = "Dummy"))
        musics.add(Music(id = 16, name = "Dummy"))
        musics.add(Music(id = 17, name = "Dummy"))
        musics.add(Music(id = 18, name = "Dummy"))
        musics.add(Music(id = 19, name = "Dummy"))
        musics.add(Music(id = 20, name = "Dummy"))
        musics.add(Music(id = 21, name = "Dummy"))
        musics.add(Music(id = 22, name = "Dummy"))
        return musics
    }
}