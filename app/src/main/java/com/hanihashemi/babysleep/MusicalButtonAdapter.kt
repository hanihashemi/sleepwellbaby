package com.hanihashemi.babysleep

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.hanihashemi.babysleep.helper.dpToPx
import com.hanihashemi.babysleep.model.Music
import com.hanihashemi.babysleep.widget.MusicalButton
import timber.log.Timber

/**
 * Created by hani on 12/24/17.
 */
class MusicalButtonAdapter(private val context: Context, private val musics: List<Music>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val musicalButton: MusicalButton

        if (convertView == null) {
            musicalButton = MusicalButton(context, null)
            musicalButton.layoutParams = ViewGroup.LayoutParams(context.dpToPx(70F), context.dpToPx(70F))
            musicalButton.setOnClickListener{onItemClick(position)}
        } else {
            musicalButton = convertView as MusicalButton
        }

        return musicalButton
    }

    private fun onItemClick(position: Int){
        Timber.d("position %s clicked", position)
    }

    override fun getItem(position: Int) = musics[position]

    override fun getItemId(position: Int) = musics[position].id

    override fun getCount() = musics.size
}