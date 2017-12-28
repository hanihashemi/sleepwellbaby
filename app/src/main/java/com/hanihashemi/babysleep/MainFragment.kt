package com.hanihashemi.babysleep

import android.view.View
import android.view.View.inflate
import android.widget.GridView
import android.widget.TextView
import com.hanihashemi.babysleep.base.BaseFragment
import com.hanihashemi.babysleep.model.Music
import com.hanihashemi.babysleep.widget.MusicalButton
import com.nex3z.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Created by hani on 12/24/17.
 */
class MainFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.main_fragment

    override fun customizeUI() {
        val musics = mutableListOf<Music>()
        musics.add(Music(0, "First 0", ""))
        musics.add(Music(1, "First 1", ""))
        musics.add(Music(2, "First 2", ""))
        musics.add(Music(3, "First 3", ""))
        musics.add(Music(4, "First 4", ""))
        musics.add(Music(5, "First 5", ""))
        musics.add(Music(6, "First 6", ""))

        val myLayout = inflate(context, R.layout.nature_section_layout, wrapperLayout)
        myLayout.findViewById<TextView>(R.id.title).text = "Hani"
//        val musicalButtonLayout = myLayout.findViewById<FlowLayout>(R.id.musicalButtonLayout)
//        musicalButtonLayout.addView(musicalButton)

        val gridView = myLayout.findViewById<GridView>(R.id.gridView)
        gridView.adapter = MusicalButtonAdapter(context, musics)


    }
}