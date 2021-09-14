package com.krpvartstudio.sshine.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.krpvartstudio.sshine.R
import com.krpvartstudio.sshine.databinding.ItemMainDailyBinding
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel

class MainDailyListAdapter : BaseAdapter<DailyWeatherListModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
        val itemMainDailyBinding = ItemMainDailyBinding.inflate(layoutInflater, parent, false)
        return DailyViewHolder(itemMainDailyBinding)
    }
inner class DailyViewHolder(private val itemMainDailyBinding: ItemMainDailyBinding) : BaseViewHolder(itemMainDailyBinding.root) {
    override fun bindView(position: Int) {
        itemMainDailyBinding.itemDailyDateTv.text = "12 Воскресенье"
        itemMainDailyBinding.itemDailyMaxtempTv.text = "30"
        itemMainDailyBinding.itemDailyMintempTv.text = "20"
        itemMainDailyBinding.itemDailyPopTv.text = "50%"
        itemMainDailyBinding.itemDailyWeatherConditionIcon.setImageResource(R.drawable.ic_sun)
    }
}
}