package com.hanihashemi.babysleep

import android.content.Intent
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
        settings.setOnClickListener {
            //            if (!MediaPlayerService.isServiceRunning(context)) {
            val intent = Intent(context, MediaPlayerService::class.java)
            intent.putExtra(MediaPlayerService.ARGUMENT.ACTION.name, MediaPlayerService.ACTION.PLAY.ordinal)
            intent.putExtra(MediaPlayerService.ARGUMENT.SONG_NAME.name, R.raw.all_pretty_horses)
            context.startService(intent)
//            }
        }

        // nature
        val natureMusics = mutableListOf<Music>()
        natureMusics.add(Music(0, R.drawable.ic_forest))
        natureMusics.add(Music(1, R.drawable.ic_sea))
        natureMusics.add(Music(2, R.drawable.ic_rain))

        //mother
        val motherMusics = mutableListOf<Music>()
        motherMusics.add(Music(3, R.drawable.ic_heart))
        motherMusics.add(Music(4, R.drawable.ic_lung))
        motherMusics.add(Music(5, R.drawable.ic_womb))

        //transport
        val transportMusics = mutableListOf<Music>()
        transportMusics.add(Music(6, R.drawable.ic_car))
        transportMusics.add(Music(7, R.drawable.ic_airplane))
        transportMusics.add(Music(8, R.drawable.ic_train))
        transportMusics.add(Music(9, R.drawable.ic_helicopter))

        //appliance
        val applianceMusics = mutableListOf<Music>()
        applianceMusics.add(Music(10, R.drawable.ic_blender))
        applianceMusics.add(Music(11, R.drawable.ic_cleaner))

        //persian songs
        val persianMusics = mutableListOf<Music>()
        persianMusics.add(Music(12, "حسنی", R.color.itemHasani))
        persianMusics.add(Music(13, "گنجشک لالا", R.color.itemGonjeshkLala))
        persianMusics.add(Music(14, "چرا", R.color.itemChera))

        //english songs
        val englishMusics = mutableListOf<Music>()
        englishMusics.add(Music(15, "Music Box", R.color.itemMusicBox))
        englishMusics.add(Music(17, "Danny Boy", R.color.itemDannyBoy))
        englishMusics.add(Music(16, "Lullabay", R.color.itemLullaby))
        englishMusics.add(Music(17, "Go to Sleep", R.color.itemGotoSleep))

        addIconSectionLayout("طبیعت", natureMusics)
        addIconSectionLayout("مادر", motherMusics)
        addIconSectionLayout("حمل و نقل", transportMusics)
        addIconSectionLayout("لوازم خانگی", applianceMusics)
        addTextSectionLayout("لالایی فارسی", persianMusics)
        addTextSectionLayout("ملودی و لالایی انگلیسی", englishMusics)

        Handler().postDelayed({ scrollView.scrollTo(0, 0) }, 100)
    }

    private fun addIconSectionLayout(name: String, natureMusics: MutableList<Music>) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        gridView.adapter = MusicalIconButtonAdapter(context, natureMusics)

        wrapperLayout.addView(myLayout)
    }

    private fun addTextSectionLayout(name: String, natureMusics: MutableList<Music>) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        gridView.adapter = MusicalTextButtonAdapter(context, natureMusics)

        wrapperLayout.addView(myLayout)
    }
}