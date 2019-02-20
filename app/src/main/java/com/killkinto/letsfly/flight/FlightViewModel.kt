package com.killkinto.letsfly.flight

import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import com.killkinto.letsfly.data.Flight
import com.killkinto.letsfly.data.FlightRepository

class FlightViewModel(val repository: FlightRepository, val context: Context) {

    val flightOutbound = ObservableArrayList<Flight>()
    val flightInbound = ObservableArrayList<Flight>()

    val time = ObservableField<String>()
    val stops = ObservableField<String>()

    fun loadFlightsData() {
        repository.list({ outbound: List<Flight>?, inbound: List<Flight>? ->
            if (outbound != null) {
                flightOutbound.addAll(outbound)
            }
            if (inbound != null) {
                flightInbound.addAll(inbound)
            }
        },
            { })
    }
}