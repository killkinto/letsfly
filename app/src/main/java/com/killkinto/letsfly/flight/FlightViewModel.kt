package com.killkinto.letsfly.flight

import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import com.killkinto.letsfly.data.Flight

class FlightViewModel(val context: Context) {

    val flights = ObservableArrayList<Flight>()

    val time = ObservableField<String>()
    val mStops = ObservableField<String>()

    var items: List<Flight> = mutableListOf()
        set(values) {
            flights.addAll(values)
        }

    private var mHours: Int? = null
    private var mMinutes: Int? = null
    private var mStopsFilter: Int? = null

    fun queryByStops(stops: Int) {
        mStopsFilter = stops
        executeFilter()
    }

    fun queryByTime(hours: Int, minutes: Int) {
        mHours = hours
        mMinutes = minutes
        executeFilter()
    }

    private fun executeFilter() {
        flights.clear()
        items.filterTo(flights) {
            if (mStops.get().isNullOrBlank()) {
                it.stops == stops
            } else {
                true//it.stops == stops
            }
        }
    }





}