package com.hanihashemi.sleepwellbaby.ui.main


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.hanihashemi.sleepwellbaby.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RecordActivityTest {

    @Suppress("unused")
    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Suppress("unused")
    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
            }
        }
    }

    @Test
    fun recordActivityTest() {
        sleep(2000)

        onView(withText("ضبط کن"))
                .perform(ViewActions.scrollTo(), click())

        sleep(3000)

        val appCompatButton = onView(
                allOf(withId(R.id.btnSave), withText("ذخیره"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                3),
                        isDisplayed()))
        appCompatButton.perform(click())

        sleep(1000)

        onView(withText("1"))
                .perform(ViewActions.scrollTo(), click())

        sleep(2000)

        val appCompatImageButton = onView(withId(R.id.playToggle))
        appCompatImageButton.perform(click())
    }
}
