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
        val itemCityListBinding = ItemMenuCitylistBinding.inflate(layoutInflater, parent,false)
        return CityListHolder(itemCityListBinding.root)
    }

    interface SearchItemClickListener{

        fun addToFavorite(item: GeoCodeModel)

        fun removeFromFavorite(item: GeoCodeModel)

        fun showWeatherIn(item: GeoCodeModel)

    }

    inner class CityListHolder(view: View): BaseViewHolder(view){

        override fun bindView(position: Int) {
            itemView.location.setOnClickListener {
                clickListener.showWeatherIn(mData[position])
            }
            itemView.favorite.setOnClickListener{
                val item = mData[position]
                when((it as MaterialButton).isChecked){
                    true->{
                        item.isFavorite=true
                        clickListener.addToFavorite(item)
                    }
                    false->{
                        item.isFavorite=false
                        clickListener.removeFromFavorite(item)
                    }
                }
            }
            mData[position].apply {
                itemView.state.text = if(!state.isNullOrEmpty()) itemView.context.getString(R.string.comma, state) else ""
                itemView.search_City.text = when(Locale.getDefault().displayLanguage){
                    "русский" -> local_names.ru?:name
                    "English" -> local_names.en?:name
                    else -> name
                }

                itemView.search_Country.text = Locale("",country).displayLanguage
                itemView.favorite.isChecked = isFavorite


            }
        }

    }
}