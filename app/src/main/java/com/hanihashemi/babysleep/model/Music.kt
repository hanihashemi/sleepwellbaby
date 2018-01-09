package com.hanihashemi.babysleep.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by hani on 12/24/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
class Music(val id: Long, val fileId: Int) : Parcelable {
    @Transient var isActive = false
    @Transient var name = ""
    @Transient var icon: Int = -1
    @Transient var color: Int = -1

    constructor(id: Long, fileId: Int, icon: Int) : this(id, fileId) {
        this.icon = icon
    }

    constructor(id: Long, fileId: Int, name: String, color: Int) : this(id, fileId) {
        this.name = name
        this.color = color
    }
}