package com.krpvartstudio.sshine.business.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.krpvartstudio.sshine.business.model.GeoCodeModel
import com.krpvartstudio.sshine.business.model.LocalNames

@Entity(tableName = "GeoCode", primaryKeys = ["lat","lon"])
class GeoCodeEntity(
    @ColumnInfo(name = "name") val name: String,
    @Embedded val local_names: LocalNames,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false
)

