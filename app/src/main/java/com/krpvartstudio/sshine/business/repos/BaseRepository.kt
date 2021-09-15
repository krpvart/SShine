package com.krpvartstudio.sshine.business.repos

import com.krpvartstudio.sshine.business.ApiProvider
import io.reactivex.rxjava3.subjects.BehaviorSubject

open class BaseRepository<T>(val api: ApiProvider) {
    val dataEmitter: BehaviorSubject<T> = BehaviorSubject.create()
}