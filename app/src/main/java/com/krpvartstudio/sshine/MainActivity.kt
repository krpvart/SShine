package com.krpvartstudio.sshine
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.krpvartstudio.sshine.databinding.ActivityMainBinding
import com.krpvartstudio.sshine.databinding.ItemMainDailyBinding
import com.krpvartstudio.sshine.databinding.ItemMainHourlyBinding
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.business.model.MainHourListModel
import com.krpvartstudio.sshine.business.model.WeatherDataModel
import com.krpvartstudio.sshine.databinding.ActivityMenuBinding
import com.krpvartstudio.sshine.presenters.MainPresenter
import com.krpvartstudio.sshine.view.*
import com.krpvartstudio.sshine.view.adapters.MainDailyListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.krpvartstudio.sshine.view.adapters.MainHourListAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit


const val TAGD = "RUN"

class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation:Location

    private lateinit var itemMainDailyBinding: ItemMainDailyBinding
    private lateinit var itemMainHourlyBinding: ItemMainHourlyBinding
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var activityMenuBinding: ActivityMenuBinding

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemMainDailyBinding = ItemMainDailyBinding.inflate(layoutInflater)
        itemMainHourlyBinding = ItemMainHourlyBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        activityMenuBinding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)




        activityMainBinding.mainHourlyList.apply {
            adapter = MainHourListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)

        }
        activityMainBinding.mainDailyList.apply {
            adapter = MainDailyListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        mainPresenter.enable()
        geoService.requestLocationUpdates(locationRequest, geoCallback, null)

    }


    //<-----moxy code-----

    override fun displayLocation(data: String) {

       Log.d(TAGD," сработало")
        activityMainBinding.mainCityNameTv.text = "UKGCity"//data
    }

    override fun displayCurentData(data: WeatherDataModel) {
        data.apply {
            activityMainBinding.mainDateTv.text = "29081989" //current.dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            activityMainBinding.mainWeatherImage.setImageResource(R.mipmap.cloud3x)
            activityMainBinding.mainWeatherIcon.setImageResource(current.weather[0].icon.provideIcon())
            activityMainBinding.mainWeatherDescriptionTv.text = current.weather[0].description
            activityMainBinding.mainTempTxt.text = current.temp.toDegre()
            daily[0].apply {
                activityMainBinding.mainMaxTempTv.text = temp.max.toDegre()
                activityMainBinding.mainMinTempTv.text = temp.min.toDegre()
            }
            activityMainBinding.mainPressureTv.text = StringBuilder().append(current.pressure.toString()).append(" hPa").toString()
            activityMainBinding.mainHumidityTv.text = StringBuilder().append(current.humidity.toString()).append(" %").toString()
            activityMainBinding.windSpeedTv.text = StringBuilder().append(current.wind_speed.toString()).append(" hPa").toString()
            activityMainBinding.mainSunriseTv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            activityMainBinding.mainSunsetTv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
        }


    }

    override fun displayHourlyData(data: List<MainHourListModel>) {
        (activityMainBinding.mainHourlyList.adapter as MainHourListAdapter).updateData(data)
    }

    override fun displayDailyData(data: List<DailyWeatherListModel>) {
        (activityMainBinding.mainDailyList.adapter as MainDailyListAdapter).updateData(data)
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