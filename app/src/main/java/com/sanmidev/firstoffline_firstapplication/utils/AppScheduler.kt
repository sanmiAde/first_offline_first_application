package com.sanmidev.firstoffline_firstapplication.utils

import com.sanmidev.firstoffline_firstapplication.di.ApplicationScope
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ApplicationScope
class AppScheduler @Inject constructor() {
    fun io() = Schedulers.io()
    fun main(): Scheduler = AndroidSchedulers.mainThread()
}