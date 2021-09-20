package com.krpvartstudio.sshine.view
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.business.model.GeoCodeModel
import com.krpvartstudio.sshine.business.model.MainHourListModel
import com.krpvartstudio.sshine.business.model.WeatherDataModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainView : MvpView{

    @AddToEndSingle
    fun displayLocation(data: String)

    @AddToEndSingle
    fun displayCurentData(data: WeatherDataModel)

    @AddToEndSingle
    fun displayHourlyData(data: List<MainHourListModel>)

    @AddToEndSingle
    fun displayDailyData(data: List<DailyWeatherListModel>)

    @AddToEndSingle
    fun displayError(error: Throwable)

    @AddToEndSingle
    fun setLoading(flag: Boolean)



}