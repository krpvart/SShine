package com.krpvartstudio.sshine.presenters

import com.krpvartstudio.sshine.business.ApiProvider
import com.krpvartstudio.sshine.business.repos.MainRepository
import com.krpvartstudio.sshine.view.MainView

class MainPresenter: BasePresenter<MainView>() {

    private val repo = MainRepository(ApiProvider())

    override fun enable() {
        repo.dataEmitter.subscribe { response ->
            viewState.displayLocation(response.cityName)
            viewState.displayCurentData(response.weatherData)
            viewState.displayDailyData(response.weatherData.daily)
            viewState.displayHourlyData(response.weatherData.hourly)
            response.error?.let {  viewState.displayError(response.error) }
        }

    }

    fun refresh(lat: String, long: String){
        viewState.setLoading(true)
    }
}