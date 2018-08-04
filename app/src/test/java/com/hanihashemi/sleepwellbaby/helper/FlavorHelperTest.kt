package com.hanihashemi.sleepwellbaby.helper

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FlavorHelperTest {

    @Test
    fun isFree() {
        assertTrue(FlavorHelper("freemiumMyket").isFree())
        assertTrue(FlavorHelper("freeiumGooglePlay").isFree())
        assertFalse(FlavorHelper("GooglePlay").isFree())
    }

    @Test
    fun isGooglePlay() {
        assertTrue(FlavorHelper("premiumGooglePlay").isGooglePlay())
        assertTrue(FlavorHelper("freeiumGooglePlay").isGooglePlay())
        assertFalse(FlavorHelper("freemiumMyket").isGooglePlay())
    }

    @Test
    fun isMyket() {
        assertTrue(FlavorHelper("freemiumMyket").isMyket())
        assertTrue(FlavorHelper("premiumMyket").isMyket())
        assertFalse(FlavorHelper("premiumGooglePlay").isMyket())
    }
}