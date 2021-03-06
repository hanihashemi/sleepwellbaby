package com.hanihashemi.sleepwellbaby.ui.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hanihashemi.sleepwellbaby.BuildConfig
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.helper.FlavorHelper
import com.hanihashemi.sleepwellbaby.model.Music
import com.hanihashemi.sleepwellbaby.widget.MusicalTextButton

/**
 * Created by hani on 12/24/17.
 */
class MusicalTextButtonAdapter(private val context: Context, private var musics: MutableList<Music>, private val onItemClick: (music: Music) -> Unit, private val onItemLongClick: (music: Music) -> Unit = {}) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button: MusicalTextButton

        if (convertView == null) {
            button = MusicalTextButton(context, null)
            button.setText(musics[position].name)
            button.setTextColor(musics[position].colorOrIcon)
            if (musics[position].isLocked && FlavorHelper(BuildConfig.FLAVOR).isFree()) button.addLock()
            button.setOnClickListener { onItemClick(musics[position]) }
            button.setOnLongClickListener {
                onItemLongClick(musics[position])
                false
            }
        } else {
            button = convertView as MusicalTextButton
        }

        if (musics[position].isActive)
            button.setBackgroundResource(R.drawable.active_music_button_background)
        else
            button.setBackgroundResource(R.drawable.music_button_background)

        return button
    }

    fun refresh(musics: List<Music>){
        this.musics.clear()
        this.musics.addAll(musics)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = musics[position]

    override fun getItemId(position: Int) = musics[position].id.toLong()

    override fun getCount() = musics.size
}