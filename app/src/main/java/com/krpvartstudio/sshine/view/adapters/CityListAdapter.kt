package com.krpvartstudio.sshine.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.krpvartstudio.sshine.business.model.GeoCodeModel
import com.krpvartstudio.sshine.databinding.ActivityMenuBinding
import com.krpvartstudio.sshine.databinding.ItemMenuCitylistBinding

class CityListAdapter: BaseAdapter<GeoCodeModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCityListBinding = ItemMenuCitylistBinding.inflate(layoutInflater, parent,false)
        return CityListHolder(itemCityListBinding)
    }

    inner class CityListHolder(private val itemMenuCitylistBinding: ItemMenuCitylistBinding): BaseViewHolder(itemMenuCitylistBinding.root){
        override fun bindView(position: Int) {
            mData[position].apply {
              //TODO ЗаполнитьСписокИзбранныхГородов
            }
        }

    }
}