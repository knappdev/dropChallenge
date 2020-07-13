package com.example.dropchallenge.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton
import io.reactivex.android.schedulers.AndroidSchedulers

@Singleton
class RxSchedulerProvider : SchedulerProvider {
    override fun computation(): Scheduler = Schedulers.computation()
    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}