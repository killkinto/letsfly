package com.killkinto.letsfly

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.killkinto.letsfly.data.FlightAdapter
import com.killkinto.letsfly.databinding.FragmentFlightsBinding
import com.killkinto.letsfly.flight.FlightViewModel

class FlightsFragment : Fragment() {

    lateinit var viewModel: FlightViewModel

    companion object {
        fun newInstance(viewModel: FlightViewModel) : FlightsFragment {
            val fragment = FlightsFragment()
            fragment.viewModel = viewModel
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadFlightsData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFlightsBinding = FragmentFlightsBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.rcvFlights.adapter = FlightAdapter(emptyList(), context!!)
        binding.rcvFlights.layoutManager = LinearLayoutManager(activity)
        binding.rcvFlights.setHasFixedSize(true)
        return binding.root
    }

}