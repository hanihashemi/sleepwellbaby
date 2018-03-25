package com.hanihashemi.babysleep.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by hani on 12/24/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
class Music(val id: Long, val fileId: Int, val name: String, val colorOrIcon: Int, var isActive: Boolean = false) : Parcelable {
    constructor(id: Long, name: String, colorOrIcon: Int, isActive: Boolean = false) : this(id, -1, name, colorOrIcon, isActive)
}