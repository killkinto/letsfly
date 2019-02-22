package com.killkinto.letsfly

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun filtrarVoos() {
        onView(
            allOf(
                withId(R.id.btn_time),
                childAtPosition(withParent(withId(R.id.viewpager)), 1),
                isDisplayed()
            )
        ).perform(click())

        onView(
            allOf(
                withId(android.R.id.button1),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")), 0
                    ), 3
                )
            )
        ).perform(scrollTo(), click())

        onView(
            allOf(
                withId(R.id.viewpager),
                childAtPosition(
                    childAtPosition(withId(android.R.id.content), 0),
                    1
                ),
                isDisplayed()
            )
        ).perform(ViewActions.swipeLeft())

        onView(
            allOf(
                childAtPosition(childAtPosition(withId(R.id.edt_stops), 0), 0),
                isDisplayed()
            )
        ).perform(ViewActions.replaceText("1"), ViewActions.closeSoftKeyboard())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
