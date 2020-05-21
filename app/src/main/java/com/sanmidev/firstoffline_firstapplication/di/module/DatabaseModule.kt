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
object DatabaseModule {


    @ApplicationScope
    @Provides
    @JvmStatic
    fun providesEmployeeDatabase(application: Application): EmployeeDatabase {
        return Room.databaseBuilder(
            application,
            EmployeeDatabase::class.java,
            EmployeeDatabase.DATABASE_NAME
        ).build()
    }

    @ApplicationScope
    @Provides
    @JvmStatic
    fun providesEmployeeDAO(employeeDatabase: EmployeeDatabase): EmployeeDAO {
        return employeeDatabase.employeeDao()
    }
}