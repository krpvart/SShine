package com.krpvartstudio.sshine.view

import com.krpvartstudio.sshine.model.DailyWeatherListModel
import com.krpvartstudio.sshine.model.MainHourListModel.MainHourListModel
import com.krpvartstudio.sshine.model.WeatherData
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainView : MvpView{

    @AddToEndSingle
    fun displayLocation(data: String)

    @AddToEndSingle
    fun displayCurentData(data: WeatherData)

    @AddToEndSingle
    fun displayHourlyData(data: List<MainHourListModel>)

    @AddToEndSingle
    fun displayDailyData(data: List<DailyWeatherListModel>)

    @AddToEndSingle
    fun displayError(error: Throwable)

    @AddToEndSingle
    fun setLoading(flag: Boolean)



}