package com.killkinto.letsfly

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val rule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.killkinto.letsfly", appContext.packageName)
    }

    @Test
    fun filtrarPorParadas() {
        onView(withId(R.id.edt_stops)).perform(typeText("1"))
        closeSoftKeyboard()
    }

    @Test
    fun filtrarPorHorario() {
        onView(withId(R.id.edt_time)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
    }

    @Test
    fun ordernarPor() {
        onView(withId(R.id.spn_order_by)).perform(click())
        onView(withText(rule.activity.resources.getStringArray(R.array.order_by_options)[0]))
            .inRoot(RootMatchers.isPlatformPopup()).perform(click())
    }

    private fun swipeLeftTab() {
        onView(withId(R.id.viewpager)).perform(swipeLeft())
    }
}

