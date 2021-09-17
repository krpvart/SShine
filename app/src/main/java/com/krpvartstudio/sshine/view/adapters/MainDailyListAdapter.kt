package com.krpvartstudio.sshine.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.krpvartstudio.sshine.R
import com.krpvartstudio.sshine.databinding.ItemMainDailyBinding
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.view.DAY_WEEK_NAME_LONG
import com.krpvartstudio.sshine.view.provideIcon
import com.krpvartstudio.sshine.view.toDateFormatOf
import com.krpvartstudio.sshine.view.toDegre
import java.lang.StringBuilder

class MainDailyListAdapter : BaseAdapter<DailyWeatherListModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
        val itemMainDailyBinding = ItemMainDailyBinding.inflate(layoutInflater, parent, false)
        return DailyViewHolder(itemMainDailyBinding)
    }
inner class DailyViewHolder(private val itemMainDailyBinding: ItemMainDailyBinding) : BaseViewHolder(itemMainDailyBinding.root) {
    override fun bindView(position: Int) {
        mData[position].apply {

            itemMainDailyBinding.itemDailyDateTv.text = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
            itemMainDailyBinding.itemDailyMaxtempTv.text = StringBuilder().append(temp.max.toDegre()).append(" \u00B0").toString()
            itemMainDailyBinding.itemDailyMintempTv.text = StringBuilder().append(temp.min.toDegre()).append(" \u00B0").toString()
            itemMainDailyBinding.itemDailyPopTv.text = pop.toString()
            itemMainDailyBinding.itemDailyWeatherConditionIcon.setImageResource(weather[0].icon.provideIcon())
        }

    }
}
}