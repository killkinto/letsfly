package com.killkinto.letsfly.data

import java.util.*

data class Flight(var stops: Int, var airline: String, var duration: Int,
                          var flightNumber: String, var from: String, var to: String,
                          var departureDate: Date, var arrivalDate: Date, var saleTotal: Double)