package com.sanmidev.firstoffline_firstapplication

import android.app.Application
import com.sanmidev.firstoffline_firstapplication.di.AppComponent
import com.sanmidev.firstoffline_firstapplication.di.DaggerAppComponent

class MainApplication : Application(){


    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}