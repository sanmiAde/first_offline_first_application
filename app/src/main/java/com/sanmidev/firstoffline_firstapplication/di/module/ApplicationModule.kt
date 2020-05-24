package com.sanmidev.firstoffline_firstapplication.di.module

import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepository
import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepositoryImpl
import com.sanmidev.firstoffline_firstapplication.di.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    @ApplicationScope
    abstract fun bindsEmployeeRepository(employeeRepositoryImpl: EmployeeRepositoryImpl) : EmployeeRepository

}