package com.hanihashemi.babysleep

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hanihashemi.babysleep.helper.dpToPx
import com.hanihashemi.babysleep.model.Music
import com.hanihashemi.babysleep.widget.MusicalTextButton
import timber.log.Timber

/**
 * Created by hani on 12/24/17.
 */
class MusicalTextButtonAdapter(private val context: Context, private val musics: List<Music>, private val onItemClick: (music: Music) -> Unit) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button: MusicalTextButton

        if (convertView == null) {
            button = MusicalTextButton(context, null)
            button.layoutParams = ViewGroup.LayoutParams(context.dpToPx(70F), context.dpToPx(70F))
            button.text = musics[position].name
            button.gravity = Gravity.CENTER
            button.setOnClickListener { onItemClick(musics[position]) }
            button.setTypeface(null, Typeface.BOLD)
            button.setTextColor(ContextCompat.getColor(context, musics[position].color))
        } else {
            button = convertView as MusicalTextButton
        }

        if (musics[position].isActive)
            button.setBackgroundResource(R.drawable.active_music_button_background)
        else
            button.setBackgroundResource(R.drawable.music_button_background)

        return button
    }

    override fun getItem(position: Int) = musics[position]

    override fun getItemId(position: Int) = musics[position].id

    override fun getCount() = musics.size
}