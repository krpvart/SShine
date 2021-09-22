package com.krpvartstudio.sshine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.view.DAY_FULL_MONTH_NAME
import com.krpvartstudio.sshine.view.provideIcon
import com.krpvartstudio.sshine.view.toDateFormatOf
import kotlinx.android.synthetic.main.fragment_day_info.*

class DailyInfoFragment: DailyBaseFragment<DailyWeatherListModel>() {

    private lateinit var viewContext: Context

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

        updateView()
    }

    override fun updateView() {
        mData?.apply {
            day_date.text = dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            day_temp.text = viewContext.getString(R.string.degree_symbol, temp.getAverage().toDegree())
            day_icon.setImageResource(weather[0].icon.provideIcon())
            day_morn_temp.text = viewContext.getString(R.string.degree_symbol, temp.morn.getAverage().toDegree())
            day_morn_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.morn.getAverage().toDegree())
            day_day_temp.text = viewContext.getString(R.string.degree_symbol, temp.day.getAverage().toDegree())
            day_day_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.day.getAverage().toDegree())
            day_eve_temp.text = viewContext.getString(R.string.degree_symbol, temp.eve.getAverage().toDegree())
            day_eve_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.eve.getAverage().toDegree())
            day_night_temp.text = viewContext.getString(R.string.degree_symbol, temp.night.getAverage().toDegree())
            day_night_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.night.getAverage().toDegree())
            day_humidity.text = ("$humidity %")






        }
    }

}