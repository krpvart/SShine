package com.krpvartstudio.sshine.business.model

data class eatherDataModel(
    val current: Current,
    val daily: List<DailyWeatherListModel>,
    val hourly: List<MainHourListModel>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)