package com.krpvartstudio.sshine.business.repos
import com.krpvartstudio.sshine.business.ApiProvider
import com.krpvartstudio.sshine.business.model.WeatherDataModel


class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api){

    fun reloadData(lat: String, lon: String){

    }

    data class ServerResponse(val cityName: String, val weatherData: WeatherDataModel, val error: Throwable? = null)
}