package com.hanihashemi.sleepwellbaby.helper

@Suppress("PrivatePropertyName")
class FlavorHelper(private val flavor: String) {
    private val FREEMIUM_MYKET = "freemiumMyket"
    private val MYKET = "Myket"
    private val FREEMIUM_GOOGLE_PLAY = "freeiumGooglePlay"
    private val GOOGLE_PLAY = "GooglePlay"

    fun isFree() = flavor == FREEMIUM_GOOGLE_PLAY || flavor == FREEMIUM_MYKET

    fun isGooglePlay() = flavor.contains(GOOGLE_PLAY)

    fun isMyket() = flavor.contains(MYKET)
}