package com.krpvartstudio.sshine.business.model

data class GeoCodeModel(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String?,
    var isFavorite: Boolean = false //TODO Будет применяться в добавлении избранных городов
)