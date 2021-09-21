package com.krpvartstudio.sshine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.view.adapters.MainDailyListAdapter
import kotlinx.android.synthetic.main.fragment_daily_list.*


class DailyListFragment: DailyBaseFragment<List<DailyWeatherListModel>>() {

    private var dailyAdapter = MainDailyListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailylist.apply {
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        mData?.let { updateView() }

        }


    override fun updateView() {
        dailyAdapter.updateData(mData!!)
    }

    private val clickListener = object : MainDailyListAdapter.DayItemClick{
        override fun showDetails(data: DailyWeatherListModel) {
            val fragment = DailyInfoFragment()
            fragment.setData(data)
            fm.beginTransaction().replace(R.id.frameContainer,fragment).addToBackStack(null).commit()

        }
    }

}