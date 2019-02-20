package com.killkinto.letsfly.remote

import com.killkinto.letsfly.data.Flight
import com.killkinto.letsfly.data.FlightDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightsApiDataSource(val flightsApi: FlightsApi) : FlightDataSource {

    companion object {
        val FLIGHT_API_TESTE_URL = "https://vcugj6hmt5.execute-api.us-east-1.amazonaws.com"
    }

    override fun list(sucess: (List<Flight>?, List<Flight>?) -> Unit, failure: () -> Unit) {
        val call = flightsApi.listFlights()

        call.enqueue(object : Callback<FlightsApiResponse> {
            override fun onResponse(call: Call<FlightsApiResponse>, response: Response<FlightsApiResponse>) {
                if (response.isSuccessful) {
                    val flightsOutbound = response.body()?.outbound
                    val flightsInbound = response.body()?.inbound

                    sucess(flightsOutbound, flightsInbound)
                } else {
                    failure()
                }
            }

            override fun onFailure(call: Call<FlightsApiResponse>, t: Throwable) {

            }
        })

    }
}