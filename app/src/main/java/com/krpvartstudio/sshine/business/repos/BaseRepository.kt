package com.krpvartstudio.sshine.business.repos

import com.krpvartstudio.sshine.App
import com.krpvartstudio.sshine.business.ApiProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

open class BaseRepository<T>(val api: ApiProvider) {
    val dataEmitter: BehaviorSubject<T> = BehaviorSubject.create()
    protected val db by lazy { App.db }

    protected fun roomTransaction(
        transaction: () -> T)
        = Observable.fromCallable   { transaction()}
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe{dataEmitter.onNext(it)}
}