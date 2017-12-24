package com.hanihashemi.babysleep

import android.view.View
import android.view.View.inflate
import android.widget.TextView
import com.hanihashemi.babysleep.base.BaseFragment
import com.hanihashemi.babysleep.widget.MusicalButton
import com.nex3z.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Created by hani on 12/24/17.
 */
class MainFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.main_fragment

    override fun customizeUI() {
        val myLayout = inflate(context, R.layout.nature_section_layout, wrapperLayout)
        myLayout.findViewById<TextView>(R.id.title).text = "Hani"
        val musicalButtonLayout = myLayout.findViewById<FlowLayout>(R.id.musicalButtonLayout)

        val musicalButton = MusicalButton(context, null, R.style.MusicalButton)

        musicalButtonLayout.addView(musicalButton)

    }
}