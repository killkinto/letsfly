package com.killkinto.letsfly.data

interface FlightDataSource {
    fun list(sucess: (List<Flight>?, List<Flight>?) -> Unit, failure: () -> Unit)
}