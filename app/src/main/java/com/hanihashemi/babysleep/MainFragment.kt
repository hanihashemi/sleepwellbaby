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

        airplane.setOnClickListener { IntentHelper().openAirplaneModeSettings(activity) }

        // nature
        val natureMusics = mutableListOf<Music>()
        natureMusics.add(Music(0, "Forest", R.drawable.ic_forest))
        natureMusics.add(Music(1, "Sea", R.drawable.ic_sea))
        natureMusics.add(Music(2, "Rain", R.drawable.ic_rain))

        //mother
        val motherMusics = mutableListOf<Music>()
        motherMusics.add(Music(3, "Heart", R.drawable.ic_heart))
        motherMusics.add(Music(4, "Lung", R.drawable.ic_lung))
        motherMusics.add(Music(5, "Womb", R.drawable.ic_womb))

        //transport
        val transportMusics = mutableListOf<Music>()
        transportMusics.add(Music(6, "Car", R.drawable.ic_car))
        transportMusics.add(Music(7, "Airplane", R.drawable.ic_airplane))
        transportMusics.add(Music(8, "Train", R.drawable.ic_train))
        transportMusics.add(Music(9, "Helicopter", R.drawable.ic_helicopter))

        //appliance
        val applianceMusics = mutableListOf<Music>()
        applianceMusics.add(Music(10, "Blender", R.drawable.ic_blender))
        applianceMusics.add(Music(11, "Cleaner", R.drawable.ic_cleaner))


        addSectionLayout("طبیعت", natureMusics)
        addSectionLayout("مادر", motherMusics)
        addSectionLayout("حمل و نقل", transportMusics)
        addSectionLayout("لوازم خانگی", applianceMusics)

        Handler().postDelayed({ scrollView.scrollTo(0, 0) }, 100)
    }

    private fun addSectionLayout(name: String, natureMusics: MutableList<Music>) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        gridView.adapter = MusicalButtonAdapter(context, natureMusics)

        wrapperLayout.addView(myLayout)
    }
}