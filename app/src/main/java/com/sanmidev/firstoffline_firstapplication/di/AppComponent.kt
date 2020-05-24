package com.sanmidev.firstoffline_firstapplication.di

import android.app.Application
import com.sanmidev.firstoffline_firstapplication.MainActivity
import com.sanmidev.firstoffline_firstapplication.di.module.ApplicationModule
import com.sanmidev.firstoffline_firstapplication.di.module.DatabaseModule
import com.sanmidev.firstoffline_firstapplication.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, DatabaseModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}