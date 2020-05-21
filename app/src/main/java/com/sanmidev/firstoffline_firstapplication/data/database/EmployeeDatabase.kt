package com.sanmidev.firstoffline_firstapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity


@Database(entities = [EmployeeEntity::class], version = 1, exportSchema = true)
abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun employeeDao() : EmployeeDAO

    companion object {
        const val DATABASE_NAME = "employee.db"
    }
}