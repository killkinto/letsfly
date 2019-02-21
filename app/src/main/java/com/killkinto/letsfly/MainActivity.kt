package com.killkinto.letsfly

import android.content.Context
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
    private lateinit var mRepository: FlightRepository
    private lateinit var mOutboundFlightsFragment: FlightsFragment
    private lateinit var mInboundFlightsFragment: FlightsFragment

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
        mRepository.list({ outbound: List<Flight>?, inbound: List<Flight>? ->
            if (outbound != null) {
                mOutboundFlightsFragment.viewModel.replaceItens(outbound)
            }
            if (inbound != null) {
                mInboundFlightsFragment.viewModel.replaceItens(inbound)
            }
        },
            { })
    }

    private fun setupViewPager() {
        mOutboundFlightsFragment = FlightsFragment.newInstance(createViewModel())
        mInboundFlightsFragment = FlightsFragment.newInstance(createViewModel())
        mFragmentAdapter = PagerAdapter(supportFragmentManager,
            listOf(mOutboundFlightsFragment, mInboundFlightsFragment), applicationContext)
        viewpager.adapter = mFragmentAdapter
        tabs.setupWithViewPager(viewpager)
    }

    private fun createViewModel(): FlightViewModel {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val retrofit = Retrofit.Builder().baseUrl(FlightsApiDataSource.FLIGHT_API_TESTE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
        val flightsApiDataSource = FlightsApiDataSource(retrofit.create(FlightsApi::class.java))
        mRepository = FlightRepository(flightsApiDataSource)
        return FlightViewModel()
    }
}

class PagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>, var context: Context) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.tab_outbound)
            1 -> context.getString(R.string.tab_inbound)
            else -> ""
        }
    }
}
