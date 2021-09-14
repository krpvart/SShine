package com.krpvartstudio.sshine.view.adapters
import com.krpvartstudio.sshine.business.model.MainHourListModel
import android.view.LayoutInflater
import android.view.ViewGroup
import com.krpvartstudio.sshine.R
import com.krpvartstudio.sshine.databinding.ItemMainHourlyBinding

class MainHourListAdapter : BaseAdapter<MainHourListModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val itemMainHourlyBinding = ItemMainHourlyBinding.inflate(layoutInflater,parent,false)
    return HourlyViewHolder(itemMainHourlyBinding)
    }

    inner class HourlyViewHolder(private val itemMainHourlyBinding: ItemMainHourlyBinding) : BaseViewHolder(itemMainHourlyBinding.root){
        override fun bindView(position: Int) {
            itemMainHourlyBinding.mainHourlyTimeTv.text = "14:00"
            itemMainHourlyBinding.mainHourlyTempTv.text = "25\u00B0"
            itemMainHourlyBinding.mainHourlyPopTv.text = "50%"
            itemMainHourlyBinding.mainHourlyImageIco.setImageResource(R.drawable.ic_sun)
        }
    }
}
