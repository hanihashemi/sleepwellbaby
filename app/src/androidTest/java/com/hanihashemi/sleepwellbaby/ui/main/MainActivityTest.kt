package com.hanihashemi.sleepwellbaby.ui.main

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.hanihashemi.sleepwellbaby.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Suppress("unused")
    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

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
    fun mainActivityTest() {
        sleep(2000)

        val imageButton = onView(
                allOf(withId(R.id.settings),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                                        0),
                                1),
                        isDisplayed()))
        imageButton.check(matches(isDisplayed()))

        val imageButton2 = onView(
                allOf(withId(R.id.airplane), isDisplayed()))
        imageButton2.check(matches(isDisplayed()))

        val textView = onView(
                allOf(withId(R.id.appName), withText("بخواب کوچولو"), isDisplayed()))
        textView.check(matches(isDisplayed()))

        onView(withText("Music Box"))
                .perform(ViewActions.scrollTo(), click())

        sleep(5000)


        val appCompatImageButton = onView(
                allOf(withId(R.id.timer),
                        childAtPosition(
                                allOf(withId(R.id.includeControlLayout),
                                        childAtPosition(
                                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                                1)),
                                0),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        sleep(1000)

        val linearLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.grid),
                        childAtPosition(
                                withId(R.id.container),
                                1)))
                .atPosition(1)
        linearLayout.perform(click())

        val appCompatImageButton2 = onView(
                allOf(withId(R.id.playToggle),
                        childAtPosition(
                                allOf(withId(R.id.includeControlLayout),
                                        childAtPosition(
                                                withClassName(`is`("android.support.constraint.ConstraintLayout")),
                                                1)),
                                1),
                        isDisplayed()))
        appCompatImageButton2.perform(click())

        val textView2 = onView(withId(R.id.txtTimer))
        textView2.check(matches(isDisplayed()))
    }
}