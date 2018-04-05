package com.hanihashemi.sleepwellbaby

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hanihashemi.sleepwellbaby.helper.dpToPx
import com.hanihashemi.sleepwellbaby.model.Music
import com.hanihashemi.sleepwellbaby.widget.MusicalIconButton

/**
 * Created by hani on 12/24/17.
 */
class MusicalIconButtonAdapter(private val context: Context, private val musics: List<Music>, private val onItemClick: (music: Music) -> Unit) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button: MusicalIconButton

        if (convertView == null) {
            button = MusicalIconButton(context, null)
            button.layoutParams = ViewGroup.LayoutParams(context.dpToPx(70F), context.dpToPx(70F))
            button.setOnClickListener { onItemClick(musics[position]) }
            button.setImageResource(musics[position].colorOrIcon)
        } else {
            button = convertView as MusicalIconButton
        }

        if (musics[position].isActive)
            button.setBackgroundResource(R.drawable.active_music_button_background)
        else
            button.setBackgroundResource(R.drawable.music_button_background)

        return button
    }

    override fun getItem(position: Int) = musics[position]

    override fun getItemId(position: Int) = musics[position].id.toLong()

    override fun getCount() = musics.size
}