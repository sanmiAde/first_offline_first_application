package com.sanmidev.firstoffline_firstapplication.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sanmidev.firstoffline_firstapplication.data.database.EmployeeDAO
import com.sanmidev.firstoffline_firstapplication.data.database.EmployeeDatabase
import com.sanmidev.firstoffline_firstapplication.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {


    @ApplicationScope
    @Provides
    fun providesEmployeeDatabase(application: Application): EmployeeDatabase {
        return Room.databaseBuilder(
            application,
            EmployeeDatabase::class.java,
            EmployeeDatabase.DATABASE_NAME
        ).build()
    }

    @ApplicationScope
    @Provides
    fun providesEmployeeDAO(employeeDatabase: EmployeeDatabase): EmployeeDAO {
        return employeeDatabase.employeeDao()
    }
}