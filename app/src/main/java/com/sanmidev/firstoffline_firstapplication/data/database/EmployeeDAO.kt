package com.sanmidev.firstoffline_firstapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity
import io.reactivex.*

@Dao
interface EmployeeDAO{

    @Query("SELECT * FROM employee_table")
    fun getEmployees() : Flowable<List<EmployeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfEmployees(employeeList : List<EmployeeEntity>) : Maybe<List<Long>>

    @Query("SELECT * FROM employee_table WHERE id = :employeeId")
    fun getEmployee(employeeId : Int) : Single<EmployeeEntity>
}