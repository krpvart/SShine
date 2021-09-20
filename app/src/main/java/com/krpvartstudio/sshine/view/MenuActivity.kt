package com.krpvartstudio.sshine.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krpvartstudio.sshine.MainActivity
import com.krpvartstudio.sshine.R
import com.krpvartstudio.sshine.business.model.GeoCodeModel
import com.krpvartstudio.sshine.databinding.ActivityMenuBinding
import com.krpvartstudio.sshine.view.adapters.CityListAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_menu.*
import moxy.MvpAppCompatActivity
import java.util.concurrent.TimeUnit



class MenuActivity : MvpAppCompatActivity(), MenuView{
    lateinit var activityMenuBinding:ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initCityList(activityMenuBinding.predictions)
        initCityList(activityMenuBinding.favorites)

        activityMenuBinding.searchField.createObservable()
            .doOnNext{setLoading(true)}
            .debounce (700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                //TODO (MenuActivity) if (!it.isNullorEmpy()) presenter.getFavoriteList()
            }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,R.anim.slide_out_left)
    }


    //<-----moxy code-----
    override fun setLoading(flag: Boolean) {
        loading.isActivated = true
        loading.visibility = if(flag) View.VISIBLE else View.GONE
    }

    override fun fillPredictionList(data: List<GeoCodeModel>) {
        (predictions.adapter as CityListAdapter).updateData(data)
    }

    override fun fillFavoriteList(data: List<GeoCodeModel>) {
        (predictions.adapter as CityListAdapter).updateData(data)
    }
    //-----moxy code----->


    private fun initCityList(rv:RecyclerView){
        val cityAdapter = CityListAdapter()
        cityAdapter.clickListener = searchItemClickListener
        rv.apply {
            adapter = cityAdapter
            layoutManager = object : LinearLayoutManager(this@MenuActivity, VERTICAL, false){
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            setHasFixedSize(true)
        }
    }

    private val searchItemClickListener = object: CityListAdapter.SearchItemClickListener {
        override fun addToFavorite(item: GeoCodeModel) {
            TODO("Not yet implemented")
        }

        override fun removeFromFavorite(item: GeoCodeModel) {
            TODO("Not yet implemented")
        }

        override fun showWeatherIn(item: GeoCodeModel) {
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("lat",item.lat.toString())
            bundle.putSerializable("lon",item.lon.toString())
            intent.putExtra("COORDINATES",bundle)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,R.anim.slide_out_left)
        }

    }

}