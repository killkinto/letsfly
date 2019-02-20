package com.killkinto.letsfly.remote

import retrofit2.Call
import retrofit2.http.GET

interface FlightsApi {
    @GET("hmg-search")
    fun listFlights(): Call<FlightsApiResponse>
}