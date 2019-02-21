package com.killkinto.letsfly.flight

import android.databinding.Observable
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import com.killkinto.letsfly.data.Flight
import java.util.*

class FlightViewModel {

    val flights = ObservableArrayList<Flight>()

    val time = ObservableField<String>()
    val stops = ObservableField<String>()

    private var mHours: Int? = null
    private var mMinutes: Int? = null
    private var mStopsFilter: Int? = null
    private var mOrder: Int = 1

    private var items: List<Flight> = mutableListOf()

    fun replaceItens(list: List<Flight>) {
        items = list
        flights.addAll(items.sortedBy { it.pricing.ota!!.saleTotal })
    }

    init {
        val callback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                mStopsFilter = stops.get()!!.toIntOrNull()
                executeFilter()
            }
        }
        stops.addOnPropertyChangedCallback(callback)
    }

    fun queryByTime(hours: Int, minutes: Int) {
        mHours = hours
        mMinutes = minutes
        time.set(String.format("%02d:%02d", mHours, mMinutes))
        executeFilter()
    }

    private fun executeFilter() {
        flights.clear()
        items.filterTo(flights) {
            (mStopsFilter == null || it.stops == mStopsFilter)
                    && filterByTime(it) }
    }

    private fun filterByTime(flight: Flight) : Boolean {
        if (mHours != null && mMinutes != null) {
            val calendar = Calendar.getInstance()
            calendar.time = flight.departureDate
            return calendar.get(Calendar.HOUR_OF_DAY) == mHours
                    && calendar.get(Calendar.MINUTE) == mMinutes
        }
        return true
    }

    fun orderBy(position: Int) {
        var items: List<Flight>
        mOrder = position

        when(position) {
            0 -> {
                items = flights.sortedByDescending { it.pricing.ota!!.saleTotal }
                flights.clear()
                flights.addAll(items)
            }
            1 -> {
                items = flights.sortedBy { it.pricing.ota!!.saleTotal }
                flights.clear()
                flights.addAll(items)
            }
            2 -> {
                items = flights.sortedBy { it.pricing.ota!!.saleTotal }
                items = items.sortedBy { it.duration }
                flights.clear()
                flights.addAll(items)
            }
        }
    }
}