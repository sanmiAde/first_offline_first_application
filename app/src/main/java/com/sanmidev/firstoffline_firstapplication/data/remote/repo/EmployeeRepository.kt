package com.sanmidev.firstoffline_firstapplication.data.remote.repo

import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeListEntity
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeListDTO
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

interface EmployeeRepository {

    fun getEmployeeFromNetwork() : Maybe<List<EmployeeEntity>>

    fun saveEmployeeListToDatabase(employeeEntities : List<EmployeeEntity>): Maybe<List<Long>>

//    fun getEmployee() : Observable<List<EmployeeEntity>>?
}