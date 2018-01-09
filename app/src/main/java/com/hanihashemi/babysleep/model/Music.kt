package com.hanihashemi.babysleep.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by hani on 12/24/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
class Music(val id: Long) : Parcelable {
    public var isActive = false
    public var name = ""
    public var icon: Int = -1
    public var color: Int = -1

    constructor(id: Long, icon: Int) : this(id) {
        this.icon = icon
    }

    constructor(id: Long, name: String, color: Int) : this(id) {
        this.name = name
        this.color = color
    }
}