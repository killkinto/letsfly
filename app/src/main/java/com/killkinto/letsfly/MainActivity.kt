package com.killkinto.letsfly

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.killkinto.letsfly.data.Flight
import com.killkinto.letsfly.data.FlightRepository
import com.killkinto.letsfly.flight.FlightViewModel
import com.killkinto.letsfly.remote.FlightsApi
import com.killkinto.letsfly.remote.FlightsApiDataSource
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mFragmentAdapter: PagerAdapter
    private lateinit var repository: FlightRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewPager()
    }

    override fun onStart() {
        super.onStart()
        loadFlightsData()
    }

    fun loadFlightsData() {
        repository.list({ outbound: List<Flight>?, inbound: List<Flight>? ->
            if (outbound != null) {
                val fragment: FlightsFragment = mFragmentAdapter.getItem(0) as FlightsFragment
                fragment.viewModel.items = outbound
            }
            if (inbound != null) {
                val fragment: FlightsFragment = mFragmentAdapter.getItem(1) as FlightsFragment
                fragment.viewModel.items = inbound
            }
        },
            { })
    }

    private fun setupViewPager() {
        mFragmentAdapter = PagerAdapter(supportFragmentManager, createViewModel())
        viewpager.adapter = mFragmentAdapter
        tabs.setupWithViewPager(viewpager)
    }

    private fun createViewModel(): FlightViewModel {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val retrofit = Retrofit.Builder().baseUrl(FlightsApiDataSource.FLIGHT_API_TESTE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
        val flightsApiDataSource = FlightsApiDataSource(retrofit.create(FlightsApi::class.java))
        repository = FlightRepository(flightsApiDataSource)
        return FlightViewModel(applicationContext)
    }
}

class PagerAdapter(fm: FragmentManager, private val viewModel: FlightViewModel) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return FlightsFragment.newInstance(viewModel)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> viewModel.context.getString(R.string.tab_outbound)
            1 -> viewModel.context.getString(R.string.tab_inbound)
            else -> ""
        }
    }
}
