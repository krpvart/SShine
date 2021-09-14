package com.krpvartstudio.sshine
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.krpvartstudio.sshine.databinding.ActivityMainBinding
import com.krpvartstudio.sshine.model.DailyWeatherListModel.DailyWeatherListModel
import com.krpvartstudio.sshine.model.MainHourListModel.MainHourListModel
import com.krpvartstudio.sshine.model.WeatherData
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

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
//        main_city_name_tv.text = "Novosibirsk"
//        main_date_tv.text = "8 september"
//        main_weather_image.setImageResource(R.mipmap.cloud3x)
//        main_weather_icon.setImageResource(R.drawable.ic_sun)
//        main_weather_description_tv.text = "Sunny"
//        main_temp_txt.text = "25\u00B0"
//        main_max_temp_tv.text = "30"
//        main_min_temp_tv.text = "20"
//        main_pressure_tv.text = "10"
//        main_humidity_tv.text = "85%"
//        wind_speed_tv.text = "6 m/s"
//        main_sunrise_tv.text = "6:00"
//        main_sunset_tv.text = "21:00"
    }

    //<-----moxy code-----

    override fun displayLocation(data: String) {
        activityMainBinding.mainCityNameTv.text = data
    }

    override fun displayCurentData(data: WeatherData) {
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