package com.krpvartstudio.sshine
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.business.model.MainHourListModel
import com.krpvartstudio.sshine.business.model.WeatherDataModel
import com.krpvartstudio.sshine.presenters.MainPresenter
import com.krpvartstudio.sshine.view.*
import kotlinx.android.synthetic.main.activity_main.*
import com.krpvartstudio.sshine.view.adapters.MainHourListAdapter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.lang.Exception
import java.lang.StringBuilder
import kotlin.math.roundToInt


class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresenter by moxyPresenter { MainPresenter() }
    private val tokenSourse: CancellationTokenSource = CancellationTokenSource()
    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation:Location

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomSheets()
        refresh.isRefreshing = true
        supportFragmentManager.beginTransaction().add(R.id.frameContainer,DailyListFragment(),DailyListFragment::class.simpleName).commit()


        if(!intent.hasExtra("COORDINATES")){
            checkGeoAvailability()
//            geoService.requestLocationUpdates(locationRequest,geoCallback,null)
            getGeo()
        }else{
            val coord = intent.extras!!.getBundle("COORDINATES")!!
            val loc = Location("")
            loc.latitude = coord.getString("lat")!!.toDouble()
            loc.longitude = coord.getString("lon")!!.toDouble()
            mLocation = loc
            mainPresenter.refresh(lat = mLocation.latitude.toString(), lon = mLocation.longitude.toString())
        }
        main_menu_btn.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out)
        }
        main_settings_btn.setOnClickListener{
                val intent = Intent(this, SettingsAcivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out)

            }
        main_hourly_list.apply {
            adapter = MainHourListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        mainPresenter.enable()
        initSwipeRefresh()

    }


    //<-----moxy code-----

    override fun displayLocation(data: String) {
      main_city_name_tv.text = data
    }

    @SuppressLint("ResourceType")
    override fun displayCurentData(data: WeatherDataModel) {
        data.apply {
            main_date_tv.text = current.dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            main_weather_image.setImageResource(R.mipmap.cloud3x)
            main_weather_icon.setImageResource(current.weather[0].icon.provideIcon())
            main_weather_description_tv.text = current.weather[0].description
            main_temp_txt.text = StringBuilder().append(current.temp.toDegre()).append("\u00B0").toString()
            daily[0].apply {
               main_max_temp_tv.text = temp.max.toDegre()
               main_min_temp_tv.text = temp.min.toDegre()
            }
            val pressureSet = SettingsHolder.pressure
            main_pressure_mu_tv.text = getString(pressureSet.nesureUnitStringRes,pressureSet.getValue(current.pressure.toDouble()))
            val windspeedSet = SettingsHolder.windSpeed
            wind_speed_mu_tv.text = getString(windspeedSet.nesureUnitStringRes, windspeedSet.getValue(current.wind_speed))
            main_humidity_tv.text = StringBuilder().append(current.humidity.toString()).append(" %").toString()
            main_sunrise_tv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            main_sunset_tv.text = current.sunset.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
        }


    }

    override fun displayHourlyData(data: List<MainHourListModel>) {
        (main_hourly_list.adapter as MainHourListAdapter).updateData(data)
    }

    override fun displayDailyData(data: List<DailyWeatherListModel>) {
        (supportFragmentManager.findFragmentByTag(DailyListFragment::class.simpleName) as DailyListFragment).setData(data)
    }

    override fun displayError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun setLoading(flag: Boolean) {
        refresh.isRefreshing = flag
    }



    //-----moxy code----->



    //<-----Location code-----
    @SuppressLint("MissingPermission")
    private fun getGeo(){
        geoService
            .getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,tokenSourse.token)
            .addOnSuccessListener {
                if(it!=null){
                    mLocation = it
                    mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
                }else{
                    displayError(Exception("Geodata is not available"))
                }
            }
    }

    private fun checkGeoAvailability(){
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnFailureListener{ exception ->
            if(exception is ResolvableApiException){
                try {
                    exception.startResolutionForResult(this,100)
                }catch (sendEx: IntentSender.SendIntentException){}
            }
        }
    }





    private fun initLocationRequest(): LocationRequest {
        val request = LocationRequest.create()
        return request.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

//    private val geoCallback = object : LocationCallback() {
//        override fun onLocationResult(geo: LocationResult) {
//            for (location in geo.locations){
//                mLocation = location;
//                mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
//
//            }
//        }
//    }

    //-----Location code----->

    private fun initBottomSheets() {
        main_bottom_sheet.isNestedScrollingEnabled = true
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        main_bottom_sheets_container.layoutParams =
            CoordinatorLayout.LayoutParams(size.x, (size.y * 0.5).roundToInt())

    }

    private fun initSwipeRefresh(){
        refresh.apply{
            setColorSchemeResources(R.color.purple_700)
            setProgressViewEndTarget(false, 280)
            setOnRefreshListener {
                mainPresenter.refresh(mLocation!!.latitude.toString(), mLocation!!.longitude.toString())
            }
        }
    }


}