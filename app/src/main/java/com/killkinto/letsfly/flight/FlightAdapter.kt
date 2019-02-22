package com.killkinto.letsfly.flight

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.killkinto.letsfly.R
import com.killkinto.letsfly.data.Flight
import com.killkinto.letsfly.databinding.FlightItemBinding
import java.text.SimpleDateFormat

class FlightAdapter(var items: List<Flight>, var context: Context) : RecyclerView.Adapter<FlightAdapter.ViewHolder>(),
    AdapterItemsContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FlightItemBinding = FlightItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun replaceItems(list: List<Any>) {
        this.items = list as List<Flight>
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: FlightItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(flight: Flight) {
            val departureTime = SimpleDateFormat(context.getString(R.string.time_mask)).format(flight.departureDate)
            val arrivalTime = SimpleDateFormat(context.getString(R.string.time_mask)).format(flight.arrivalDate)
            val time = "$departureTime-$arrivalTime"
            var duration = flight.duration
            var durationStr = ""
            val hours = duration / 60
            if (hours > 0) {
                duration %= 60
                durationStr = "$hours h"
            }
            durationStr += "$duration min"
            val fromTo = "${flight.from}-${flight.to}"
            val stops = "${flight.stops} parada(s)"
            var saleTotal = "0,00"
            if (flight.pricing.ota != null) {
                saleTotal = flight.pricing.ota!!.saleTotal.toString().replace('.', ',')
            }

            binding.txtDepartureArrival.text = time
            binding.txtDuration.text = durationStr
            binding.txtFromTo.text = fromTo
            binding.txtStops.text = stops
            binding.txtCia.text = flight.airline.capitalize()
            binding.txtPriceTotal.text = context.getString(R.string.price_flight_format, saleTotal)
            binding.executePendingBindings()
        }
    }
}