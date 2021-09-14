package com.krpvartstudio.sshine.presenters

import com.krpvartstudio.sshine.view.MainView

class MainPresenter: BasePresenter<MainView>() {
    override fun enable() {

    }

    fun refresh(lat: String, long: String){
        viewState.setLoading(true)
    }
}