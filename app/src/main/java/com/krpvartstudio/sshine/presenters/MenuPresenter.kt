package com.krpvartstudio.sshine.presenters

import com.krpvartstudio.sshine.business.ApiProvider
import com.krpvartstudio.sshine.business.model.GeoCodeModel
import com.krpvartstudio.sshine.business.repos.MenuRepository
import com.krpvartstudio.sshine.business.repos.SAVED
import com.krpvartstudio.sshine.view.MenuView

class MenuPresenter : BasePresenter<MenuView>(){
    private val repo = MenuRepository(ApiProvider())
    override fun enable() {
        repo.dataEmitter.subscribe {
            viewState.setLoading(false)
            if (it.purpose == SAVED) {
                viewState.fillFavoriteList(it.data)
            } else {
                viewState.fillPredictionList(it.data)
            }
        }
    }

    fun searchForIt(str:String){
        repo.getCities(str)
    }

    fun removeLocation(data: GeoCodeModel){
        repo.remove(data)
    }

    fun saveLocation(data: GeoCodeModel){
        repo.add(data)
    }

    fun getFavoriteList(){
        repo.updateFavorite()
    }
}