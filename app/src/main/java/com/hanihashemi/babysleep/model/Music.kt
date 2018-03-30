package com.hanihashemi.babysleep.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.File

/**
 * Created by hani on 12/24/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
class Music(val id: Int, val fileId: Int, val name: String, val colorOrIcon: Int, var isActive: Boolean = false, var file: File? = null) : Parcelable {
    constructor(id: Int, name: String, colorOrIcon: Int, isActive: Boolean = false) : this(id, -1, name, colorOrIcon, isActive)
    constructor(id: Int, name:String, colorOrIcon: Int, isActive: Boolean = false, file: File) : this(id, -1, name, colorOrIcon, isActive, file)
}