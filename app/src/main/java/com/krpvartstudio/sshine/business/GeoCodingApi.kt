package com.krpvartstudio.sshine.business

import com.krpvartstudio.sshine.business.model.GeoCodeModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {

    @GET("geo/1.0/reverse?")
    fun getCityByCoord(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: String = "10",
        @Query("appid") appid: String = "62a90c5aa8cd182da50fb6aea06c4228"
    ): Observable<List<GeoCodeModel>>

}