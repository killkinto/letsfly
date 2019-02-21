package com.killkinto.letsfly

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import com.killkinto.letsfly.flight.FlightViewModel
import java.util.*

class TimePickerFragment() : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var viewModel: FlightViewModel

    companion object {
        fun newInstance(viewModel: FlightViewModel) : TimePickerFragment {
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
