package com.hanihashemi.sleepwellbaby.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.BaseAdapter
import android.widget.SeekBar
import android.widget.TextView
import com.hanihashemi.sleepwellbaby.MediaPlayerService
import com.hanihashemi.sleepwellbaby.MusicalIconButtonAdapter
import com.hanihashemi.sleepwellbaby.MusicalTextButtonAdapter
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.base.BaseFragment
import com.hanihashemi.sleepwellbaby.helper.IntentHelper
import com.hanihashemi.sleepwellbaby.model.Music
import com.hanihashemi.sleepwellbaby.ui.record.RecordActivity
import com.hanihashemi.sleepwellbaby.widget.ExpandableGridView
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
    private var seekBarTouching = false
    var voiceItemsAdapter: MusicalTextButtonAdapter? = null

    override fun customizeUI() {
        airplane.setOnClickListener { IntentHelper().openAirplaneModeSettings(activity) }
        playToggle.setOnClickListener { onPlayToggleClick() }
        timer.setOnClickListener { onTimerClick() }
        settings.setOnClickListener { onSettingsClick() }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                seekBarTouching = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBarTouching = false
                val intent = Intent(context, MediaPlayerService::class.java)
                intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.SEEK_TO)
                intent.putExtra(MediaPlayerService.ARGUMENTS.SEEK_TO_MILLIS.name, seekBar?.progress)
                context.startService(intent)
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
        })
        seekBar.progressDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        seekBar.thumb.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        syncRequest()

        // nature
        val natureMusics = mutableListOf<Music>()
        natureMusics.add(Music(0, R.raw.forest, "Forest", R.drawable.ic_forest))
        natureMusics.add(Music(1, R.raw.sea, "Sea", R.drawable.ic_sea))
        natureMusics.add(Music(2, R.raw.rain, "Rain", R.drawable.ic_rain))

        //mother
        val motherMusics = mutableListOf<Music>()
        motherMusics.add(Music(3, R.raw.heart, "Hear", R.drawable.ic_heart))
        motherMusics.add(Music(4, R.raw.lung, "Lung", R.drawable.ic_lung))
        motherMusics.add(Music(5, R.raw.womb, "Womb", R.drawable.ic_womb))

        //transport
        val transportMusics = mutableListOf<Music>()
        transportMusics.add(Music(6, R.raw.car, "Car", R.drawable.ic_car))
        transportMusics.add(Music(7, R.raw.airplane, "Airplane", R.drawable.ic_airplane))
        transportMusics.add(Music(8, R.raw.train, "Train", R.drawable.ic_train))
        transportMusics.add(Music(9, R.raw.helicopter, "Helicopter", R.drawable.ic_helicopter))

        //appliance
        val applianceMusics = mutableListOf<Music>()
        applianceMusics.add(Music(10, R.raw.blender, "Blender", R.drawable.ic_blender))
        applianceMusics.add(Music(11, R.raw.cleaner, "Cleaner", R.drawable.ic_cleaner))

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
        addVoiceSectionLayout("صدای شما")

        Handler().postDelayed({ scrollView.scrollTo(0, 0) }, 100)
    }

    override fun onStart() {
        super.onStart()
        updateVoiceFiles()
    }

    private fun updateVoiceFiles() {
        if (musicList.size == 0)
            return

        removeVoicesFromMusic()

        val voices = mutableListOf<Music>()
        voices.add(Music(20, "ضبط کن", R.color.itemAddVoice))

        val audioFile = ContextCompat.getDataDir(context)
        audioFile.listFiles { file -> file?.path?.endsWith(".aac") ?: false }
                .forEachIndexed { index, file -> voices.add(Music(21 + index, (index + 1).toString(), R.color.itemAddVoice, false, file)) }

        voiceItemsAdapter?.refresh(voices)
        musicList.addAll(voices)
    }

    private fun removeVoicesFromMusic() = musicList.removeAll { it.id >= 20 }

    private fun onSettingsClick() {
        IntentHelper().sendMail(activity, "jhanihashemi@gmail.com", "نظر و یا پیشنهاد", "")
    }

    private fun onTimerClick() {
        BottomSheet.Builder(activity)
                .setSheet(R.menu.timer)
                .setStyle(R.style.BottomSheet)
                .setTitle("قطع آهنگ")
                .setCancelable(true)
                .setListener(object : BottomSheetListener {
                    override fun onSheetDismissed(p0: BottomSheet, p1: Any?, p2: Int) {

                    }

                    override fun onSheetShown(p0: BottomSheet, p1: Any?) {

                    }

                    override fun onSheetItemSelected(p0: BottomSheet, item: MenuItem?, p2: Any?) {
                        var millis = 0L
                        when (item?.itemId) {
                            R.id.fiveMinutes -> {
                                millis = 300000L
                            }
                            R.id.fifteenMinutes -> {
                                millis = 900000L
                            }
                            R.id.thirtyMinutes -> {
                                millis = 18000000L
                            }
                            R.id.oneHour -> {
                                millis = 3600000L
                            }
                        }

                        val intent = Intent(context, MediaPlayerService::class.java)
                        intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.SET_SLEEP_TIMER)
                        intent.putExtra(MediaPlayerService.ARGUMENTS.SLEEP_TIMER_MILLIS.name, millis)
                        context.startService(intent)
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

    private fun addVoiceSectionLayout(name: String) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        voiceItemsAdapter = MusicalTextButtonAdapter(context, mutableListOf(), { music -> onVoiceItemClick(music) }, { music -> onVoiceItemLongClick(music) })

        gridView.adapter = voiceItemsAdapter
        wrapperLayout.addView(myLayout)
        adapterList.add(voiceItemsAdapter!!)

        updateVoiceFiles()
    }

    private fun onVoiceItemClick(music: Music) {
        when (music.id) {
            20 -> {
                VoiceRecordPermission(activity, {
                    RecordActivity.start(context)
                }).check()
            }
            else -> onItemClick(music)
        }
    }

    private fun onVoiceItemLongClick(music: Music) {
        if (music.id == 20)
            return

        val builder = AlertDialog.Builder(activity)
        builder.setMessage("می خواهید این صدا را حذف کنید؟")
                .setPositiveButton("بله", { _, _ ->
                    music.file?.delete()
                    updateVoiceFiles()
                })
                .setNegativeButton("خیر", null)
        builder.create().show()
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
            fun resetMusics(elseId: Int?) = musicList.forEach { item -> item.isActive = item.id == elseId }

            fun notifyAllAdapters() = adapterList.forEach { item -> item.notifyDataSetChanged() }

            val status = intent?.getSerializableExtra(MediaPlayerService.BROADCAST_ARG_STATUS)
            val music = intent?.getParcelableExtra<Music>(MediaPlayerService.BROADCAST_ARG_MUSIC)
            val millisUntilFinished = intent?.getLongExtra(MediaPlayerService.BROADCAST_ARG_MILLIS_UNTIL_FINISHED, 0)
            val duration = intent?.getIntExtra(MediaPlayerService.BROADCAST_ARG_DURATION, 0) ?: 0
            val currentPosition = intent?.getIntExtra(MediaPlayerService.BROADCAST_ARG_CURRENT_POSITION, 0)
                    ?: 0

            seekBar.max = duration
            if (!seekBarTouching)
                seekBar.progress = currentPosition
            txtTimer.text = convertMillisToTime(millisUntilFinished)

            when (status) {
                MediaPlayerService.STATUS.PLAYING,
                MediaPlayerService.STATUS.PAUSE -> {
                    lastPlayerStatus = status as MediaPlayerService.STATUS
                    resetMusics(music?.id)
                    notifyAllAdapters()
                    includeControlLayout.visibility = View.VISIBLE
                    seekBar.visibility = View.VISIBLE
                    playToggle.setImageResource(if (status == MediaPlayerService.STATUS.PLAYING) R.drawable.ic_pause else R.drawable.ic_play)
                }
                MediaPlayerService.STATUS.STOP -> {
                    lastPlayerStatus = status as MediaPlayerService.STATUS
                    resetMusics()
                    notifyAllAdapters()
                    includeControlLayout.visibility = View.GONE
                    seekBar.visibility = View.GONE
                }
            }
        }
    }

    private fun convertMillisToTime(millisUntilFinished: Long?): String {
        val second = millisUntilFinished!! / 1000 % 60
        val minute = millisUntilFinished / (1000 * 60) % 60

        val time = String.format("%02d:%02d", minute, second)
        return if (time == "00:00") "" else time
    }
}