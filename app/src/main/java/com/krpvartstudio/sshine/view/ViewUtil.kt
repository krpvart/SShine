package com.krpvartstudio.sshine.view

import android.text.Editable
import com.google.android.material.textfield.TextInputEditText
import com.krpvartstudio.sshine.R
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import android.text.TextWatcher as TextWatcher

const val DAY_FULL_MONTH_NAME = "dd MMMM"
const val DAY_WEEK_NAME_LONG = "dd EEEE"
const val HOUR_DOUBLE_DOT_MINUTE = "HH:mm"

fun Long.toDateFormatOf(format: String): String{
    val cal = Calendar.getInstance()
    val timeZone = cal.timeZone
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    sdf.timeZone = timeZone
    return sdf.format(Date(this*1000))
}

fun Double.toDegre() = SettingsHolder.temp.getValue(this)

fun Double.toPercentString(extraPart: String = "") = (this*100).roundToInt().toString() + extraPart

fun String.provideIcon() = when(this){
    "01n","01d" -> R.drawable.ic_01d
    "02n","02d" -> R.drawable.ic_02d
    "03n","03d" -> R.drawable.ic_02d
    "04n","04d" -> R.drawable.ic_03d
    "09n","09d" -> R.drawable.ic_09d
    "10n","10d" -> R.drawable.ic_10d
    "11n","11d" -> R.drawable.ic_11d
    "13n","13d" -> R.drawable.ic_13d
    "50n","50d" -> R.drawable.ic_50d
    else -> R.drawable.ic_error
}

fun TextInputEditText.createObservable() : Flowable<String>{
    return Flowable.create(
        {
            addTextChangedListener(object : SimpleTextWatcher(){
                override fun afterTextChanged(s: Editable?) {
                    it.onNext(s.toString())
                }
            })
        }, BackpressureStrategy.BUFFER
    )
}

abstract class SimpleTextWatcher : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}
