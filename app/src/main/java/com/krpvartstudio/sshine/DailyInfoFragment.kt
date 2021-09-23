package  com.krpvartstudio.sshine

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.view.*
import kotlinx.android.synthetic.main.fragment_day_info.*

class DailyInfoFragment: DailyBaseFragment<DailyWeatherListModel>() {

    private lateinit var viewContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_right)
        enterTransition = inflater.inflateTransition(R.transition.slide_out_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day_info,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_back.setOnClickListener{
            fm.popBackStack()
        }
        viewContext = view.context
        updateView()

    }


    override fun updateView() {
        mData?.apply {
            day_date.text = dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            day_temp.text = viewContext.getString(R.string.degree_symbol, temp.getAverage().toDegre())
            day_icon.setImageResource(weather[0].icon.provideIcon())
            day_morn_temp.text = viewContext.getString(R.string.degree_symbol, temp.morn.toDegre())
            day_morn_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.morn.toDegre())
            day_day_temp.text = viewContext.getString(R.string.degree_symbol, temp.day.toDegre())
            day_day_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.day.toDegre())
            day_eve_temp.text = viewContext.getString(R.string.degree_symbol, temp.eve.toDegre())
            day_eve_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.eve.toDegre())
            day_night_temp.text = viewContext.getString(R.string.degree_symbol, temp.night.toDegre())
            day_night_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.night.toDegre())
            day_humidity.text = ("$humidity %")
            val settingsPressure = SettingsHolder.pressure
            day_pressure.text = viewContext.getString(settingsPressure.nesureUnitStringRes,settingsPressure.getValue(pressure.toDouble()))
            val settingsWindSpeed = SettingsHolder.windSpeed
            day_pressure.text = viewContext.getString(settingsPressure.nesureUnitStringRes,settingsWindSpeed.getValue(wind_speed))
            day_wind_direction.text = wind_deg.toString()
            day_sunrise.text = sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            day_sunset.text = sunset.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)



        }
    }

}