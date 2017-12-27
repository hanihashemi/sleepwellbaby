package com.hanihashemi.babysleep

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.hanihashemi.babysleep.model.Music

/**
 * Created by hani on 12/24/17.
 */
class MusicalButtonAdapter(private val context: Context, private val musics: List<Music>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val button: Button
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            button = Button(context)
            button.layoutParams = ViewGroup.LayoutParams(85, 85)
            button.setPadding(8, 8, 8, 8)
        } else {
            button = convertView as Button
        }

        button.text = musics[position].name
        return button
    }

    override fun getItem(index: Int) = musics[index]

    override fun getItemId(index: Int) = musics[index].id

    override fun getCount() = musics.size
}