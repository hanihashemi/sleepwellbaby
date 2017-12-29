package com.hanihashemi.babysleep

import android.os.Handler
import android.view.View.inflate
import android.widget.TextView
import com.hanihashemi.babysleep.base.BaseFragment
import com.hanihashemi.babysleep.helper.IntentHelper
import com.hanihashemi.babysleep.model.Music
import com.hanihashemi.babysleep.widget.ExpandableGridView
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment_header.*

/**
 * Created by hani on 12/24/17.
 */
class MainFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.main_fragment

    override fun customizeUI() {
        val musics = mutableListOf<Music>()
        musics.add(Music(0, "First 0", R.drawable.ic_cleaner, false))

        val myLayout = inflate(context, R.layout.section_layout, wrapperLayout)
        myLayout.findViewById<TextView>(R.id.title).text = "طبیعت"

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        gridView.adapter = MusicalButtonAdapter(context, musics)

        Handler().postDelayed({scrollView.scrollTo(0, 0)}, 100)

        airplane.setOnClickListener {
            IntentHelper().openAirplaneModeSettings(activity)
        }
    }
}