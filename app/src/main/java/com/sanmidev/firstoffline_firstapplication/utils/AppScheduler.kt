package com.sanmidev.firstoffline_firstapplication.utils

import com.sanmidev.firstoffline_firstapplication.di.ApplicationScope
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ApplicationScope
class AppScheduler @Inject constructor()  : com.sanmidev.firstoffline_firstapplication.utils.Scheduler{
    override fun io() = Schedulers.io()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}

class  TestAppScheduler() : com.sanmidev.firstoffline_firstapplication.utils.Scheduler{

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun main(): Scheduler = Schedulers.trampoline()
}

interface Scheduler {
    fun io(): Scheduler

    fun main(): Scheduler
}