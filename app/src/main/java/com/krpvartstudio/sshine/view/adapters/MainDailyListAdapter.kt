package com.krpvartstudio.sshine.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.krpvartstudio.sshine.R
import com.krpvartstudio.sshine.databinding.ItemMainDailyBinding
import com.krpvartstudio.sshine.business.model.DailyWeatherListModel
import com.krpvartstudio.sshine.view.*
import java.lang.StringBuilder

class MainDailyListAdapter : BaseAdapter<DailyWeatherListModel>() {


    lateinit var clickListener: DayItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
        val itemMainDailyBinding = ItemMainDailyBinding.inflate(layoutInflater, parent, false)
        return DailyViewHolder(itemMainDailyBinding)
    }

    interface DayItemClick{
        fun showDetails(data:DailyWeatherListModel)
    }

    inner class DailyViewHolder(private val itemMainDailyBinding: ItemMainDailyBinding) : BaseViewHolder(itemMainDailyBinding.root) {


    override fun bindView(position: Int) {
        val itemData = mData[position]
       // val defaultTextColor = itemMainDailyBinding.itemDailyDateTv.textColors
        if(position==0){
            this.setIsRecyclable(false)
            itemMainDailyBinding.itemDailyDateTv.setTextColor(ContextCompat.getColor(itemMainDailyBinding.itemDailyDateTv.context,R.color.purple_500))
        }

//        if (position==0){itemMainDailyBinding.itemDailyDateTv
//            .setTextColor(ContextCompat.getColor(itemMainDailyBinding.itemDailyDateTv.context,R.color.purple_500))}
//        else{
//            itemMainDailyBinding.itemDailyDateTv.setTextColor(defaultTextColor)
//        }

        itemMainDailyBinding.dayContainer.setOnClickListener {
            clickListener.showDetails(itemData)
        }

        if(mData.isNotEmpty()){
            itemData.apply {
                val dateToDay = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
                itemMainDailyBinding.itemDailyDateTv.text = if(dateToDay.startsWith("0",true)) dateToDay.removePrefix("0") else dateToDay

                if(pop<0.01){
                    itemMainDailyBinding.itemDailyPopTv.visibility = View.INVISIBLE
                }else{
                    itemMainDailyBinding.itemDailyPopTv.visibility = View.VISIBLE
                    itemMainDailyBinding.itemDailyPopTv.text = pop.toPercentString("%")
                }
                itemMainDailyBinding.itemDailyWeatherConditionIcon.setImageResource(weather[0].icon.provideIcon())
                itemMainDailyBinding.itemDailyMaxtempTv.text = StringBuilder().append(temp.max.toDegre()).append("\u00B0").toString()
                itemMainDailyBinding.itemDailyMintempTv.text = StringBuilder().append(temp.min.toDegre()).append("\u00B0").toString()
            }
        }
    }
}
}