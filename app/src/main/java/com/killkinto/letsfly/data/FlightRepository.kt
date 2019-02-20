package com.killkinto.letsfly.data

class FlightRepository(private val flightDataSource: FlightDataSource) : FlightDataSource {
    override fun list(sucess: (List<Flight>?, List<Flight>?) -> Unit, failure: () -> Unit) {
        flightDataSource.list(sucess, failure)
    }
}