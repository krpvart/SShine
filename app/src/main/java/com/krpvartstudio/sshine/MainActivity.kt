package com.krpvartstudio.sshine
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.krpvartstudio.sshine.databinding.ActivityMainBinding
import com.krpvartstudio.sshine.databinding.ItemMainDailyBinding
import com.krpvartstudio.sshine.databinding.ItemMainHourlyBinding
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.business.model.MainHourListModel
import com.krpvartstudio.sshine.business.model.Weather
import com.krpvartstudio.sshine.presenters.MainPresenter
import com.krpvartstudio.sshine.view.MainView
import com.krpvartstudio.sshine.view.adapters.MainDailyListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.krpvartstudio.sshine.view.adapters.MainHourListAdapter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter



class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation:Location

    private lateinit var itemMainDailyBinding: ItemMainDailyBinding
    private lateinit var itemMainHourlyBinding: ItemMainHourlyBinding
    private lateinit var activityMainBinding: ActivityMainBinding

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemMainHourlyBinding = ItemMainHourlyBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initViews()
        main_hourly_list.apply {
            adapter = MainHourListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)

        }
        main_daily_list.apply {
            adapter = MainDailyListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        mainPresenter.enable()
        geoService.requestLocationUpdates(locationRequest, geoCallback, null)

    }

    private fun initViews() {
        activityMainBinding.mainCityNameTv.text = "Новосибирск"
        activityMainBinding.mainDateTv.text = "13 сентября"
        activityMainBinding.mainWeatherImage.setImageResource(R.mipmap.cloud3x)
        activityMainBinding.mainWeatherIcon.setImageResource(R.drawable.ic_sun)
        activityMainBinding.mainWeatherDescriptionTv.text = "Солнечно"
        activityMainBinding.mainTempTxt.text = "25\u00B0"
        activityMainBinding.mainMaxTempTv.text = "30"
        activityMainBinding.mainMinTempTv.text = "20"
        activityMainBinding.mainPressureTv.text = "10"
        activityMainBinding.mainHumidityTv.text = "85%"
        activityMainBinding.windSpeedTv.text = "6 м/сек"
        activityMainBinding.mainSunriseTv.text = "6:00"
        activityMainBinding.mainSunsetTv.text = "21:00"
    }

    //<-----moxy code-----

    override fun displayLocation(data: String) {
        activityMainBinding.mainCityNameTv.text = data
    }

    override fun displayCurentData(data: Weather) {
        activityMainBinding.mainCityNameTv.text = "Новосибирск"
        activityMainBinding.mainDateTv.text = "13 сентября"
        activityMainBinding.mainWeatherImage.setImageResource(R.mipmap.cloud3x)
        activityMainBinding.mainWeatherIcon.setImageResource(R.drawable.ic_sun)
        activityMainBinding.mainWeatherDescriptionTv.text = "Солнечно"
        activityMainBinding.mainTempTxt.text = "25\u00B0"
        activityMainBinding.mainMaxTempTv.text = "30"
        activityMainBinding.mainMinTempTv.text = "20"
        activityMainBinding.mainPressureTv.text = "10"
        activityMainBinding.mainHumidityTv.text = "85%"
        activityMainBinding.windSpeedTv.text = "6 м/сек"
        activityMainBinding.mainSunriseTv.text = "6:00"
        activityMainBinding.mainSunsetTv.text = "21:00"
    }

    override fun displayHourlyData(data: List<MainHourListModel>) {
        (main_hourly_list.adapter as MainHourListAdapter).updateData(data)
    }

    override fun displayDailyData(data: List<DailyWeatherListModel>) {
        (main_daily_list.adapter as MainDailyListAdapter).updateData(data)
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flag: Boolean) {

    }

    //-----moxy code----->


    //<-----Location code-----
    private fun initLocationRequest(): LocationRequest {
        val request = LocationRequest.create()
        return request.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(geo: LocationResult) {
            for (location in geo.locations){
                mLocation = location;
                mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
            }
        }
    }



    //-----Location code----->


}