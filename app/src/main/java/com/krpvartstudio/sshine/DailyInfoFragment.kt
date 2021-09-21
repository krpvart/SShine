package com.krpvartstudio.sshine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel

class DailyInfoFragment: DailyBaseFragment<DailyWeatherListModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_list,container,false)
    }

    override fun updateView() {
        TODO("Not yet implemented")
    }

}