package com.krpvartstudio.sshine.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.google.android.material.button.MaterialButton
import com.krpvartstudio.sshine.R
import com.krpvartstudio.sshine.business.model.GeoCodeModel
import com.krpvartstudio.sshine.databinding.ActivityMenuBinding
import com.krpvartstudio.sshine.databinding.ItemMenuCitylistBinding
import kotlinx.android.synthetic.main.item_menu_citylist.view.*
import java.util.*

class CityListAdapter: BaseAdapter<GeoCodeModel>() {

    lateinit var clickListener: SearchItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemMenuCitylistBinding = ItemMenuCitylistBinding.inflate(layoutInflater, parent,false)
        return CityListHolder(itemMenuCitylistBinding)
    }

    interface SearchItemClickListener{

        fun addToFavorite(item: GeoCodeModel)

        fun removeFromFavorite(item: GeoCodeModel)

        fun showWeatherIn(item: GeoCodeModel)

    }

    inner class CityListHolder(private val itemMenuCitylistBinding: ItemMenuCitylistBinding) :
        BaseViewHolder(itemMenuCitylistBinding.root) {

        override fun bindView(position: Int) {
            itemMenuCitylistBinding.apply {
                location.setOnClickListener {
                    clickListener.showWeatherIn(mData[position])
                }
                favorite.setOnClickListener {
                    val item = mData[position]
                    when ((it as MaterialButton).isChecked) {
                        true -> {
                            item.isFavorite = true
                            clickListener.addToFavorite(item)
                        }
                        false -> {
                            item.isFavorite = false
                            clickListener.removeFromFavorite(item)
                        }
                    }
                }
                mData[position].apply {
                    itemMenuCitylistBinding.state.text =
                        if (!state.isNullOrEmpty()) itemMenuCitylistBinding.root.context.getString(
                            R.string.comma,
                            state
                        ) else ""
                    itemMenuCitylistBinding.searchCity.text =
                        when (Locale.getDefault().displayLanguage) {
                            "русский" -> local_names.ru ?: name
                            "English" -> local_names.en ?: name
                            else -> name
                        }

                    itemMenuCitylistBinding.searchCountry.text = country
                    itemMenuCitylistBinding.favorite.isChecked = isFavorite


                }
            }
        }

    }
}