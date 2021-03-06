package com.krpvartstudio.sshine.business.repos
import android.util.Log
import com.google.gson.Gson
import com.krpvartstudio.sshine.business.ApiProvider
import com.krpvartstudio.sshine.business.model.WeatherDataModel
import com.krpvartstudio.sshine.business.room.WeatherDataEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*


class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api){

    private val gson = Gson()
    private val dbAcces = db.getWeatherDao()
    private val defLanguage = when (Locale.getDefault().displayLanguage) {
        "русский" -> "ru"
        else -> "en"
    }

    fun reloadData(lat: String, lon: String){
        Observable.zip(
            api.providerWeatherApi().getWeatherForecast(lat, lon, lang = defLanguage),
            api.providerGeoCodeApi().getCityByCoord(lat, lon).map {
                it.asSequence()
                    .map { model ->
                        when (Locale.getDefault().displayLanguage){
                            "русский" -> model.local_names.ru
                            "English" -> model.local_names.en
                        else -> model.name}
                        }
                    .toList()
                    .filterNotNull()
                    .first()
            },
            { weatherData, geoCode -> ServerResponse(geoCode, weatherData) }
        )

            .subscribeOn(Schedulers.io())
            .doOnNext{
                dbAcces.insertWeatherData(WeatherDataEntity(data = gson.toJson(it.weatherData),city = it.cityName))
            }
            .onErrorResumeNext{
                Observable.just(ServerResponse(
                    dbAcces.getWeatherData().city,
                    gson.fromJson(dbAcces.getWeatherData().data, WeatherDataModel::class.java),
                    it
                ))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    dataEmitter.onNext(it)
                },
                {
                }
            )

    }



    data class ServerResponse(val cityName: String, val weatherData: WeatherDataModel, val error: Throwable? = null)
}