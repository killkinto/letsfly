package com.killkinto.letsfly

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import com.killkinto.letsfly.flight.FlightAdapter
import com.killkinto.letsfly.databinding.FragmentFlightsBinding
import com.killkinto.letsfly.flight.FlightViewModel
import java.util.*

class FlightsFragment : Fragment() {

    lateinit var viewModel: FlightViewModel

    companion object {
        fun newInstance(viewModel: FlightViewModel): FlightsFragment {
            val fragment = FlightsFragment()
            fragment.viewModel = viewModel
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentFlightsBinding = FragmentFlightsBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.rcvFlights.adapter = FlightAdapter(emptyList(), context!!)
        binding.rcvFlights.layoutManager = LinearLayoutManager(activity)
        binding.rcvFlights.setHasFixedSize(true)

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            activity, R.array.order_by_options,
            android.R.layout.simple_spinner_item
        )
        binding.spnOrderBy.adapter = adapter
        binding.spnOrderBy.setSelection(1)
        binding.spnOrderBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.orderBy(position)
            }
        }

        binding.btnTime.setOnClickListener {
            TimePickerFragment.newInstance(viewModel).show(fragmentManager, "timePicker")
        }

        return binding.root
    }
}

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var viewModel: FlightViewModel

    companion object {
        fun newInstance(viewModel: FlightViewModel): TimePickerFragment {
            val fragment = TimePickerFragment()
            fragment.viewModel = viewModel
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.queryByTime(hourOfDay, minute)
    }
}