package com.hanihashemi.sleepwellbaby.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.File

/**
 * Created by hani on 12/24/17.
 */
@SuppressLint("ParcelCreator")
@Parcelize
class Music(
        val id: Int,
        val fileId: Int,
        val name: String,
        val colorOrIcon: Int,
        var isLocked: Boolean = false,
        var isActive: Boolean = false,
        var file: File? = null) : Parcelable {

    constructor(id: Int,
                name: String,
                colorOrIcon: Int = -1,
                isLocked: Boolean = false,
                isActive: Boolean = false) : this(id, -1, name, colorOrIcon, isLocked, isActive)

    constructor(id: Int,
                name: String,
                colorOrIcon: Int = -1,
                file: File,
                isLocked: Boolean = false,
                isActive: Boolean = false) : this(id, -1, name, colorOrIcon, isLocked, isActive, file)

    override fun equals(other: Any?) = (other != null && other is Music && other.id == this.id)

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + fileId
        result = 31 * result + name.hashCode()
        result = 31 * result + colorOrIcon
        result = 31 * result + isLocked.hashCode()
        result = 31 * result + isActive.hashCode()
        result = 31 * result + (file?.hashCode() ?: 0)
        return result
    }
}