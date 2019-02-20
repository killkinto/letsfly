package com.killkinto.letsfly.remote

import com.killkinto.letsfly.data.Flight

data class FlightsApiResponse(var outbound: List<Flight>, var inbound: List<Flight>)