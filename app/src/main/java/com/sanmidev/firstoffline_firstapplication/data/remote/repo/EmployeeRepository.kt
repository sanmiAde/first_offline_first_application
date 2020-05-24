package com.sanmidev.firstoffline_firstapplication.data.remote.repo

import arrow.core.Either
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeListEntity
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeListDTO
import com.sanmidev.firstoffline_firstapplication.data.remote.ErrorDTO
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response


interface EmployeeRepository {

    fun getEmployeeFromNetwork() : Maybe<List<EmployeeEntity>>

    fun getEmployeeFromNetworkObservable() : Maybe<Either<ErrorDTO, List<EmployeeEntity>>>

   // fun saveEmployeeListToDatabase(employeeEntities : List<EmployeeEntity>): Maybe<List<Long>>

//    fun getEmployee() : Observable<List<EmployeeEntity>>?
}