package com.krpvartstudio.sshine.business
import com.krpvartstudio.sshine.business.model.WeatherDataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/onecall?")
    fun getWeaterForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("appid") appid: String = "62a90c5aa8cd182da50fb6aea06c4228",
        @Query("lang") lang: String = "en"
    ) : Observable<WeatherDataModel>
}