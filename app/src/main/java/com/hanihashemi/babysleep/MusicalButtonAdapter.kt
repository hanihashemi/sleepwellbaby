package com.hanihashemi.babysleep

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.hanihashemi.babysleep.helper.dpToPx
import com.hanihashemi.babysleep.model.Music
import com.hanihashemi.babysleep.widget.MusicalButton

/**
 * Created by hani on 12/24/17.
 */
class MusicalButtonAdapter(private val context: Context, private val musics: List<Music>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val musicalButton: MusicalButton

        if (convertView == null) {
            musicalButton = MusicalButton(context, null)
            musicalButton.layoutParams = ViewGroup.LayoutParams(context.dpToPx(70F), context.dpToPx(70F))
        } else {
            musicalButton = convertView as MusicalButton
        }

        return musicalButton
    }

    override fun getItem(index: Int) = musics[index]

    override fun getItemId(index: Int) = musics[index].id

    override fun getCount() = musics.size
}