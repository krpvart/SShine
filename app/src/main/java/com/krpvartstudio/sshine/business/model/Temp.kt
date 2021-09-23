package com.krpvartstudio.sshine.business.model

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
) {
    fun getAverage() = (morn+day+eve+night)/4
}