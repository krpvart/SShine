package com.krpvartstudio.sshine.view.adapters
import com.krpvartstudio.sshine.business.model.MainHourListModel
import android.view.LayoutInflater
import android.view.ViewGroup
import com.krpvartstudio.sshine.R
import com.krpvartstudio.sshine.databinding.ItemMainHourlyBinding
import com.krpvartstudio.sshine.view.*
import java.lang.StringBuilder

class MainHourListAdapter : BaseAdapter<MainHourListModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val itemMainHourlyBinding = ItemMainHourlyBinding.inflate(layoutInflater,parent,false)
    return HourlyViewHolder(itemMainHourlyBinding)
    }

    inner class HourlyViewHolder(private val itemMainHourlyBinding: ItemMainHourlyBinding) : BaseViewHolder(itemMainHourlyBinding.root){

        override fun bindView(position: Int) {
            mData[position].apply {
                itemMainHourlyBinding.mainHourlyTimeTv.text = dt.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
                itemMainHourlyBinding.mainHourlyTempTv.text = StringBuilder().append(temp.toDegre()).append(" \u00B0").toString()
                itemMainHourlyBinding.mainHourlyPopTv.text = pop.toPercentString()
                itemMainHourlyBinding.mainHourlyImageIco.setImageResource(weather[position].icon.provideIcon())
            }


        }
    }
}
