package com.krpvartstudio.sshine.business.repos
import com.krpvartstudio.sshine.business.ApiProvider
import com.krpvartstudio.sshine.business.model.WeatherDataModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api){

    fun reloadData(lat: String, lon: String){
        Observable.zip(
            api.providerWeatherApi().getWeaterForecast(lat, lon),
            api.providerGeoCodeApi().getCityByCoord(lat, lon).map {
                it.asSequence()
                    .map { model -> model.name }
                    .toList()
                    .filterNotNull()
                    .first()
            },
            { weatherData, geoCode -> ServerResponse(geoCode, weatherData) }
        )

            .subscribeOn(Schedulers.io())
            .doOnNext{/*TODO Тут будет добавление в БД*/}
            /*.onErrorResumeNext{} TODO Тут будет извеление объекта из БД*/

    }



    data class ServerResponse(val cityName: String, val weatherData: WeatherDataModel, val error: Throwable? = null)
}

