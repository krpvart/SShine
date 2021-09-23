package com.krpvartstudio.sshine.business.model

import com.krpvartstudio.sshine.business.room.GeoCodeEntity


data class GeoCodeModel(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String?,
    var isFavorite: Boolean = false
){
    fun mapToEntity() = GeoCodeEntity(
    name = name,
    lat = lat,
    lon = lon,
    country = country,
    state = state?: "",
    isFavorite = isFavorite,
    local_names = local_names)
}