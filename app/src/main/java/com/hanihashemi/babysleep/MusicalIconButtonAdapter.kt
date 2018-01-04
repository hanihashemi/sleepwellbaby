package com.hanihashemi.babysleep

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hanihashemi.babysleep.helper.dpToPx
import com.hanihashemi.babysleep.model.Music
import com.hanihashemi.babysleep.widget.MusicalIconButton
import timber.log.Timber

/**
 * Created by hani on 12/24/17.
 */
class MusicalIconButtonAdapter(private val context: Context, private val musics: List<Music>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button: MusicalIconButton

        if (convertView == null) {
            button = MusicalIconButton(context, null)
            button.layoutParams = ViewGroup.LayoutParams(context.dpToPx(70F), context.dpToPx(70F))
            button.setOnClickListener{onItemClick(position)}
            button.setImageResource(musics[position].icon)
            if (musics[position].isActive)
                button.setBackgroundResource(R.drawable.active_music_button_background)
            else
                button.setBackgroundResource(R.drawable.music_button_background)
        } else {
            button = convertView as MusicalIconButton
        }

        return button
    }

    private fun onItemClick(position: Int){
        Timber.d("position %s clicked", position)
    }

    override fun getItem(position: Int) = musics[position]

    override fun getItemId(position: Int) = musics[position].id

    override fun getCount() = musics.size
}