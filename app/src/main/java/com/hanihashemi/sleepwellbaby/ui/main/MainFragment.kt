package com.hanihashemi.sleepwellbaby.ui.main

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.View.inflate
import android.widget.BaseAdapter
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.hanihashemi.sleepwellbaby.BuildConfig
import com.hanihashemi.sleepwellbaby.R
import com.hanihashemi.sleepwellbaby.base.BaseFragment
import com.hanihashemi.sleepwellbaby.helper.AudioFileHelper
import com.hanihashemi.sleepwellbaby.helper.FlavorHelper
import com.hanihashemi.sleepwellbaby.helper.IntentHelper
import com.hanihashemi.sleepwellbaby.helper.TimeHelper
import com.hanihashemi.sleepwellbaby.model.Music
import com.hanihashemi.sleepwellbaby.service.MediaPlayerService
import com.hanihashemi.sleepwellbaby.ui.feedback.FeedbackDialog
import com.hanihashemi.sleepwellbaby.ui.main.adapter.MusicalIconButtonAdapter
import com.hanihashemi.sleepwellbaby.ui.main.adapter.MusicalTextButtonAdapter
import com.hanihashemi.sleepwellbaby.ui.record.RecordActivity
import com.hanihashemi.sleepwellbaby.ui.upgrade.UpgradeActivity
import com.hanihashemi.sleepwellbaby.widget.ExpandableGridView
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment_footer.*
import kotlinx.android.synthetic.main.main_fragment_header.*

/**
 * Created by hani on 12/24/17.
 */
class MainFragment : BaseFragment() {
    override val layoutResource: Int get() = R.layout.main_fragment
    val musicManager = MusicManager()
    private val adapterList = mutableListOf<BaseAdapter>()
    private var lastPlayerStatus = MediaPlayerService.STATUS.STOP
    var seekBarTouching = false
    private val countOfDefaultMusics = 20

    override fun customizeUI() {
        setActions()
        initSeekBar()
        syncRequest()
        addDefaultMusics()
        Handler().postDelayed({ scrollView.scrollTo(0, 0) }, 100)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun addDefaultMusics() {
        // nature
        val natureMusics = mutableListOf<Music>()
        natureMusics.add(Music(0, R.raw.forest, "Forest", R.drawable.ic_forest, isLocked = true))
        natureMusics.add(Music(1, R.raw.sea, "Sea", R.drawable.ic_sea))
        natureMusics.add(Music(2, R.raw.rain, "Rain", R.drawable.ic_rain))

        //mother
        val motherMusics = mutableListOf<Music>()
        motherMusics.add(Music(3, R.raw.heart, "Hear", R.drawable.ic_heart))
        motherMusics.add(Music(4, R.raw.lung, "Lung", R.drawable.ic_lung))
        motherMusics.add(Music(5, R.raw.womb, "Womb", R.drawable.ic_womb, isLocked = true))

        //transport
        val transportMusics = mutableListOf<Music>()
        transportMusics.add(Music(6, R.raw.car, "Car", R.drawable.ic_car))
        transportMusics.add(Music(7, R.raw.airplane, "Airplane", R.drawable.ic_airplane))
        transportMusics.add(Music(8, R.raw.train, "Train", R.drawable.ic_train, isLocked = true))
        transportMusics.add(Music(9, R.raw.helicopter, "Helicopter", R.drawable.ic_helicopter, isLocked = true))

        //appliance
        val applianceMusics = mutableListOf<Music>()
        applianceMusics.add(Music(10, R.raw.blender, "Blender", R.drawable.ic_blender))
        applianceMusics.add(Music(11, R.raw.cleaner, "Cleaner", R.drawable.ic_cleaner))

        //persian songs
        val persianMusics = mutableListOf<Music>()
        persianMusics.add(Music(12, R.raw.hasani, getString(R.string.main_track_hasani), R.color.itemHasani, isLocked = true))
        persianMusics.add(Music(13, R.raw.gonjeshk, getString(R.string.main_track_gonjeshk_lala), R.color.itemGonjeshkLala))
        persianMusics.add(Music(14, R.raw.chera, getString(R.string.main_track_chera), R.color.itemChera))

        //english songs
        val englishMusics = mutableListOf<Music>()
        englishMusics.add(Music(15, R.raw.music_box, getString(R.string.main_track_music_box), R.color.itemMusicBox))
        englishMusics.add(Music(16, R.raw.danny_boy, getString(R.string.main_track_danny_boy), R.color.itemDannyBoy))
        englishMusics.add(Music(17, R.raw.lullaby, getString(R.string.main_track_lullaby), R.color.itemLullaby, isLocked = true))
        englishMusics.add(Music(18, R.raw.goto_sleep, getString(R.string.main_track_go_to_sleep), R.color.itemGotoSleep))
        englishMusics.add(Music(19, R.raw.all_pretty_horses, getString(R.string.main_track_pretty_horses), R.color.itemAllPrettyHorses, isLocked = true))

        addIconSectionLayout(getString(R.string.main_track_nature), natureMusics)
        addIconSectionLayout(getString(R.string.main_track_mom), motherMusics)
        addIconSectionLayout(getString(R.string.main_track_transport), transportMusics)
        addIconSectionLayout(getString(R.string.main_track_appliance), applianceMusics)
        addTextSectionLayout(getString(R.string.main_track_english_lullaby), englishMusics)
        if (!FlavorHelper(BuildConfig.FLAVOR).isMyket())
            addTextSectionLayout(getString(R.string.main_track_persian_lullaby), persianMusics)
        addVoiceSectionLayout(getString(R.string.main_track_your_sounds))
    }

    private fun initSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                seekBarTouching = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBarTouching = false
                val intent = Intent(context, MediaPlayerService::class.java)
                intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.SEEK_TO)
                intent.putExtra(MediaPlayerService.ARGUMENTS.SEEK_TO_MILLIS.name, seekBar?.progress)
                context?.startService(intent)
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
        })
        seekBar.progressDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        seekBar.thumb.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
    }

    private fun setActions() {
        if (activity == null)
            return

        airplane.setOnClickListener { IntentHelper().openAirplaneModeSettings(activity!!) }
        playToggle.setOnClickListener { onPlayToggleClick() }
        timer.setOnClickListener { showBottomSheet(activity as MainActivity) }
        settings.setOnClickListener {
            FeedbackDialog().show(fragmentManager, FeedbackDialog::class.java.simpleName)
        }
    }

    fun updateVoiceFiles() {
        if (musicManager.isEmpty() || context == null)
            return

        musicManager.removeAfter(countOfDefaultMusics)

        val voices = mutableListOf<Music>()
        voices.add(Music(countOfDefaultMusics, getString(R.string.main_track_record), R.color.itemAddVoice))

        val directoryFiles = AudioFileHelper(context!!).list()
        if (directoryFiles != null)
            directoryFiles
                    .forEachIndexed { index, file ->
                        voices.add(Music(
                                countOfDefaultMusics + index + 1, (index + 1).toString(),
                                R.color.itemAddVoice, file))
                    }

        musicManager.addAll(voices)

        val voiceItemsAdapter = MusicalTextButtonAdapter(
                context!!, voices,
                { music -> onVoiceItemClick(music) },
                { music -> onVoiceItemLongClick(music) })
        gridView.adapter = voiceItemsAdapter
    }

    private fun onPlayToggleClick() {
        when (lastPlayerStatus) {
            MediaPlayerService.STATUS.PLAYING -> {
                val intent = Intent(context, MediaPlayerService::class.java)
                intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.PAUSE)
                context?.startService(intent)
            }
            MediaPlayerService.STATUS.PAUSE -> {
                val intent = Intent(context, MediaPlayerService::class.java)
                intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.PLAY)
                context?.startService(intent)
            }
            else -> {
            }
        }
    }

    private fun syncRequest() {
        val intent = Intent(context, MediaPlayerService::class.java)
        intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.SYNC)
        context?.startService(intent)
    }

    private fun addIconSectionLayout(name: String, musics: MutableList<Music>) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        val musicalIconButtonAdapter = MusicalIconButtonAdapter(context!!, musics, { music -> onItemClick(music) })

        gridView.adapter = musicalIconButtonAdapter
        wrapperLayout.addView(myLayout)
        adapterList.add(musicalIconButtonAdapter)
        musicManager.addAll(musics)
    }

    private fun addTextSectionLayout(name: String, musics: MutableList<Music>) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        val gridView = myLayout.findViewById<ExpandableGridView>(R.id.gridView2)
        val musicTextButtonAdapter = MusicalTextButtonAdapter(context!!, musics, { music -> onItemClick(music) })

        gridView.adapter = musicTextButtonAdapter
        wrapperLayout.addView(myLayout)
        adapterList.add(musicTextButtonAdapter)
        musicManager.addAll(musics)
    }

    lateinit var gridView: ExpandableGridView

    private fun addVoiceSectionLayout(name: String) {
        val myLayout = inflate(context, R.layout.section_layout, null)
        myLayout.findViewById<TextView>(R.id.title).text = name

        gridView = myLayout.findViewById(R.id.gridView2)
        wrapperLayout.addView(myLayout)

        updateVoiceFiles()
    }

    private fun onVoiceItemClick(music: Music) {
        when (music.id) {
            countOfDefaultMusics -> {
                if (FlavorHelper(BuildConfig.FLAVOR).isFree() && AudioFileHelper(context!!).size() > 2)
                    UpgradeActivity.start(context!!)
                else
                    VoiceRecordPermission(activity!!).check(activity!!)
            }
            else -> onItemClick(music)
        }
    }

    private fun onVoiceItemLongClick(music: Music) {
        if (music.id == 20)
            return

        val builder = AlertDialog.Builder(activity!!)
        builder.setMessage(getString(R.string.main_remove_my_sound_question))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    music.file?.delete()
                    updateVoiceFiles()
                }
                .setNegativeButton(getString(R.string.no), null)
        builder.create().show()
    }

    private fun onItemClick(music: Music) {
        if (music.isLocked && FlavorHelper(BuildConfig.FLAVOR).isFree()) {
            UpgradeActivity.start(context!!)
            return
        }

        val intent = Intent(context, MediaPlayerService::class.java)
        intent.putExtra(MediaPlayerService.ARGUMENTS.ACTION.name, MediaPlayerService.ACTIONS.PLAY)
        intent.putExtra(MediaPlayerService.ARGUMENTS.MUSIC_OBJ.name, music)
        context?.startService(intent)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(context!!).registerReceiver(messageReceiver, IntentFilter(MediaPlayerService.BROADCAST_KEY))
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(messageReceiver)
        super.onPause()
    }

    fun notifyAllAdapters() {
        (gridView.adapter as MusicalTextButtonAdapter).notifyDataSetChanged()
        adapterList.forEach { item -> item.notifyDataSetChanged() }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val status = intent?.getSerializableExtra(MediaPlayerService.BROADCAST_ARG_STATUS)
            val music = intent?.getParcelableExtra<Music>(MediaPlayerService.BROADCAST_ARG_MUSIC)
            val millisUntilFinish = intent?.getLongExtra(MediaPlayerService.BROADCAST_ARG_MILLIS_UNTIL_FINISHED, 0)
            val duration = intent?.getIntExtra(MediaPlayerService.BROADCAST_ARG_DURATION, 0) ?: 0
            val currentPosition = intent?.getIntExtra(MediaPlayerService.BROADCAST_ARG_CURRENT_POSITION, 0)
                    ?: 0

            seekBar.max = duration
            if (!seekBarTouching)
                seekBar.progress = currentPosition
            txtTimer.text = TimeHelper().convertMillisToTime(millisUntilFinish)

            when (status) {
                MediaPlayerService.STATUS.PLAYING,
                MediaPlayerService.STATUS.PAUSE -> {
                    lastPlayerStatus = status as MediaPlayerService.STATUS
                    musicManager.resetActiveMusics(music)
                    notifyAllAdapters()
                    includeControlLayout.visibility = View.VISIBLE
                    seekBar.visibility = View.VISIBLE
                    playToggle.setImageResource(if (status == MediaPlayerService.STATUS.PLAYING) R.drawable.ic_pause else R.drawable.ic_play)
                }
                MediaPlayerService.STATUS.STOP -> {
                    lastPlayerStatus = status as MediaPlayerService.STATUS
                    musicManager.resetActiveMusics()
                    notifyAllAdapters()
                    includeControlLayout.visibility = View.GONE
                    seekBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RecordActivity.requestCode && resultCode == Activity.RESULT_OK)
            updateVoiceFiles()
    }
}