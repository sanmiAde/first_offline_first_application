package com.sanmidev.firstoffline_firstapplication.data.remote.repo

import android.util.Log
import com.sanmidev.firstoffline_firstapplication.data.database.EmployeeDAO
import com.sanmidev.firstoffline_firstapplication.data.database.EmployeeDatabase
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeListEntity
import com.sanmidev.firstoffline_firstapplication.data.mapper.EmployeeMapper
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeAPI
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeListDTO
import com.sanmidev.firstoffline_firstapplication.utils.AppScheduler
import com.sanmidev.firstoffline_firstapplication.utils.utils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val employeeAPI: EmployeeAPI,
    private val employeeMapper: EmployeeMapper,
    private val employeeDAO: EmployeeDAO,
    private val appScheduler: AppScheduler,
    private val utils: utils
) : EmployeeRepository {

    override fun getEmployeeFromNetwork(): Maybe<List<EmployeeEntity>> {

        return employeeAPI.getEmployees().map { employeeDTO: EmployeeListDTO ->

            employeeMapper.transformToEntity(employeeDTO.employeeDto)

        }
    }

    override fun saveEmployeeListToDatabase(employeeEntities: List<EmployeeEntity>): Maybe<List<Long>> {
        return  employeeDAO.insertListOfEmployees(employeeEntities)
    }

//    override fun getEmployee(): Observable<List<EmployeeEntity>> {
//        val hasConnection = utils.isConnectedToInternet()
//        var employeeFromAPIMaybeOservable : Maybe<List<EmployeeEntity>> ? = null
//
//        if(hasConnection){
//            employeeFromAPIMaybeOservable = getEmployeeFromNetwork()
//        }
//
//        val employeeFromDatabaseMaybeOservable = getEmployeeFromDB().toObservable()
//
//       return if(hasConnection){
//            Observable.concatArrayEager(employeeFromAPIMaybeOservable?.toObservable(), employeeFromDatabaseMaybeOservable)
//        }else{
//           employeeFromDatabaseMaybeOservable
//       }
//
//    }

   fun getEmployeeFromDB(): Flowable<List<EmployeeEntity>> {
        return employeeDAO.getEmployees().doOnNext {
            Log.d("Repo", it.size.toString())
        }
    }


}