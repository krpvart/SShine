package com.krpvartstudio.sshine
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
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
import com.krpvartstudio.sshine.business.model.GeoCodeModel
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
import kotlin.math.roundToInt


class MainActivity : MvpAppCompatActivity(), MainView {

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation:Location

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomSheets()
        initSwipeRefresh()

        refresh.isRefreshing = true
        supportFragmentManager.beginTransaction().add(R.id.frameContainer,DailyListFragment(),DailyListFragment::class.simpleName).commit()

        if(!intent.hasExtra("COORDINATES")){
            geoService.requestLocationUpdates(locationRequest,geoCallback,null)
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

            }

            main_hourly_list.apply {
            adapter = MainHourListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        mainPresenter.enable()


    }


    //<-----moxy code-----

    override fun displayLocation(data: String) {
      main_city_name_tv.text = data
    }

    override fun displayCurentData(data: WeatherDataModel) {
        data.apply {
            main_date_tv.text = current.dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            main_weather_image.setImageResource(R.mipmap.cloud3x)
            main_weather_icon.setImageResource(current.weather[0].icon.provideIcon())
            main_weather_description_tv.text = current.weather[0].description
            main_temp_txt.text = current.temp.toDegre()
            daily[0].apply {
               main_max_temp_tv.text = temp.max.toDegre()
               main_min_temp_tv.text = temp.min.toDegre()
            }

            val pressureSet = SettingsHolder.pressure

            main_pressure_mu_tv.text = getString(pressureSet.nesureUnitStringRes,pressureSet.getValue(current.pressure.toDouble()))

            val windspeedSet = SettingsHolder.windSpeed
            wind_speed_mu_tv.text = getString(windspeedSet.nesureUnitStringRes, windspeedSet.getValue(current.wind_speed))

            main_pressure_mu_tv.text = StringBuilder().append(current.humidity.toString()).append(" %").toString()
            main_sunrise_tv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            main_sunset_tv.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
        }


    }

    override fun displayHourlyData(data: List<MainHourListModel>) {
        (main_hourly_list.adapter as MainHourListAdapter).updateData(data)
    }

    override fun displayDailyData(data: List<DailyWeatherListModel>) {
        (supportFragmentManager.findFragmentByTag(DailyListFragment::class.simpleName) as DailyListFragment).setData(data)
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flag: Boolean) {
        refresh.isRefreshing = flag
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