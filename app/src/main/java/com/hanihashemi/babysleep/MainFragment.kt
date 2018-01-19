package com.hanihashemi.babysleep

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.support.v4.content.LocalBroadcastManager
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.BaseAdapter
import android.widget.TextView
import com.hanihashemi.babysleep.base.BaseFragment
import com.hanihashemi.babysleep.helper.IntentHelper
import com.hanihashemi.babysleep.model.Music
import com.hanihashemi.babysleep.widget.ExpandableGridView
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment_footer.*
import kotlinx.android.synthetic.main.main_fragment_header.*


/**
 * Created by hani on 12/24/17.
 */
class MainFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.main_fragment
    private val musicList = mutableListOf<Music>()
    private val adapterList = mutableListOf<BaseAdapter>()
    private var lastPlayerStatus = MediaPlayerService.STATUS.STOP

    override fun customizeUI() {
        airplane.setOnClickListener { IntentHelper().openAirplaneModeSettings(activity) }
        playToggle.setOnClickListener { onPlayToggleClick() }
        timer.setOnClickListener { onTimerClick() }
        syncRequest()

        // nature
        val natureMusics = mutableListOf<Music>()
        natureMusics.add(Music(0, R.raw.forest, R.drawable.ic_forest))
        natureMusics.add(Music(1, R.raw.sea, R.drawable.ic_sea))
        natureMusics.add(Music(2, R.raw.rain, R.drawable.ic_rain))

        //mother
        val motherMusics = mutableListOf<Music>()
        motherMusics.add(Music(3, R.raw.heart, R.drawable.ic_heart))
        motherMusics.add(Music(4, R.raw.lung, R.drawable.ic_lung))
        motherMusics.add(Music(5, R.raw.womb, R.drawable.ic_womb))

        //transport
        val transportMusics = mutableListOf<Music>()
        transportMusics.add(Music(6, R.raw.car, R.drawable.ic_car))
        transportMusics.add(Music(7, R.raw.airplane, R.drawable.ic_airplane))
        transportMusics.add(Music(8, R.raw.train, R.drawable.ic_train))
        transportMusics.add(Music(9, R.raw.helicopter, R.drawable.ic_helicopter))

        //appliance
        val applianceMusics = mutableListOf<Music>()
        applianceMusics.add(Music(10, R.raw.blender, R.drawable.ic_blender))
        applianceMusics.add(Music(11, R.raw.cleaner, R.drawable.ic_cleaner))

        //persian songs
        val persianMusics = mutableListOf<Music>()
        persianMusics.add(Music(12, R.raw.hasani, "حسنی", R.color.itemHasani))
        persianMusics.add(Music(13, R.raw.gonjeshk, "گنجشک لالا", R.color.itemGonjeshkLala))
        persianMusics.add(Music(14, R.raw.chera, "چرا", R.color.itemChera))

        //english songs
        val englishMusics = mutableListOf<Music>()
        englishMusics.add(Music(15, R.raw.music_box, "Music Box", R.color.itemMusicBox))
        englishMusics.add(Music(16, R.raw.danny_boy, "Danny Boy", R.color.itemDannyBoy))
        englishMusics.add(Music(17, R.raw.lullaby, "Lullabay", R.color.itemLullaby))
        englishMusics.add(Music(18, R.raw.goto_sleep, "Go to Sleep", R.color.itemGotoSleep))
        englishMusics.add(Music(19, R.raw.all_pretty_horses, "Pretty Horses", R.color.itemAllPrettyHorses))

        addIconSectionLayout("طبیعت", natureMusics)
        addIconSectionLayout("مادر", motherMusics)
        addIconSectionLayout("حمل و نقل", transportMusics)
        addIconSectionLayout("لوازم خانگی", applianceMusics)
        addTextSectionLayout("لالایی فارسی", persianMusics)
        addTextSectionLayout("ملودی و لالایی انگلیسی", englishMusics)

        Handler().postDelayed({ scrollView.scrollTo(0, 0) }, 100)
    }

    private fun onTimerClick() {
        BottomSheet.Builder(activity)
                .setSheet(R.menu.timer)
                .setTitle("قطغ آهنگ بعد از ")
                .setCancelable(true)
                .setListener(object : BottomSheetListener {
                    override fun onSheetDismissed(p0: BottomSheet, p1: Any?, p2: Int) {

                    }

                    override fun onSheetShown(p0: BottomSheet, p1: Any?) {

                    }

                    override fun onSheetItemSelected(p0: BottomSheet, item: MenuItem?, p2: Any?) {
                        when (item?.itemId) {
                            R.id.never -> {

                            }
                            R.id.fiveMinutes -> {

                            }
                            R.id.fifteenMinutes -> {

                            }
                            R.id.thirtyMinutes -> {

                            }
                            R.id.oneHour -> {

                            }
                        }
                    }
                })
                .show()
    }

    private fun onPlayToggleClick() {
        when (lastPlayerStatus) {
            MediaPlayerService.STATUS.PLAYING -> {
                val intent = Intent(context, MediaPlayerService::class.java)
                intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.PAUSE)
                context.startService(intent)
            }
            MediaPlayerService.STATUS.PAUSE -> {
                val intent = Intent(context, MediaPlayerService::class.java)
                intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.PLAY)
                context.startService(intent)
            }
            else -> {
            }
        }
    }

    private fun syncRequest() {
        val intent = Intent(context, MediaPlayerService::class.java)
        intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.SYNC)
        context.startService(intent)
    }

    private fun addIconSectionLayout(name: String, musics: MutableList<Music>) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        val musicalIconButtonAdapter = MusicalIconButtonAdapter(context, musics, { music -> onItemClick(music) })

        gridView.adapter = musicalIconButtonAdapter
        wrapperLayout.addView(myLayout)
        adapterList.add(musicalIconButtonAdapter)
        musicList.addAll(musics)
    }

    private fun addTextSectionLayout(name: String, musics: MutableList<Music>) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        val musicTextButtonAdapter = MusicalTextButtonAdapter(context, musics, { music -> onItemClick(music) })

        gridView.adapter = musicTextButtonAdapter
        wrapperLayout.addView(myLayout)
        adapterList.add(musicTextButtonAdapter)
        musicList.addAll(musics)
    }

    private fun onItemClick(music: Music) {
        val intent = Intent(context, MediaPlayerService::class.java)
        intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.PLAY)
        intent.putExtra(MediaPlayerService.ARGUMENTS.MUSIC_OBJ.name, music)
        context.startService(intent)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(context).registerReceiver(messageReceiver, IntentFilter(MediaPlayerService.BROADCAST_KEY))
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(messageReceiver)
        super.onPause()
    }

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            fun resetMusics() = musicList.forEach { item -> item.isActive = false }
            fun resetMusics(elseId: Long?) = musicList.forEach { item -> item.isActive = item.id == elseId }

            fun notifyAllAdapters() = adapterList.forEach { item -> item.notifyDataSetChanged() }

            val status = intent?.getSerializableExtra(MediaPlayerService.BROADCAST_ARG_STATUS)
            val music = intent?.getParcelableExtra<Music>(MediaPlayerService.BROADCAST_ARG_MUSIC)
            lastPlayerStatus = status as MediaPlayerService.STATUS

            when (status) {
                MediaPlayerService.STATUS.PLAYING,
                MediaPlayerService.STATUS.PAUSE -> {
                    resetMusics(music?.id)
                    notifyAllAdapters()
                    includeControlLayout.visibility = View.VISIBLE
                    playToggle.setImageResource(if (status == MediaPlayerService.STATUS.PLAYING) R.drawable.ic_pause else R.drawable.ic_play)
                }
                MediaPlayerService.STATUS.STOP -> {
                    resetMusics()
                    notifyAllAdapters()
                    includeControlLayout.visibility = View.GONE
                }
            }
        }

    }
}