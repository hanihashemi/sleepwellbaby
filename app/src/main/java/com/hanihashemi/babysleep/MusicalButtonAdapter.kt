package com.hanihashemi.babysleep

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hanihashemi.babysleep.model.Music

/**
 * Created by hani on 12/24/17.
 */
class MusicalButtonAdapter(val context: Context, val musics: List<Music>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        
    }

    override fun getItem(index: Int) = musics[index]

    override fun getItemId(p0: Int): Long {

    }

    override fun getCount() = musics.size
}