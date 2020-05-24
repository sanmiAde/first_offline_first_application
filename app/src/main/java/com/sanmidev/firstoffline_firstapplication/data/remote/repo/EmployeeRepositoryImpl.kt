package com.sanmidev.firstoffline_firstapplication.data.remote.repo

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity
import com.sanmidev.firstoffline_firstapplication.data.mapper.EmployeeMapper
import com.sanmidev.firstoffline_firstapplication.data.remote.*
import com.squareup.moshi.Moshi
import io.reactivex.Maybe
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val employeeAPI: EmployeeAPI,
    private val employeeMapper: EmployeeMapper,
    private val moshi: Moshi

) : EmployeeRepository {

    override fun getEmployeeFromNetwork(): Maybe<List<EmployeeEntity>> {

        return employeeAPI.getEmployees().map { employeeDTO: EmployeeListDTO ->

            employeeMapper.transformToEntity(employeeDTO.employeeDto)

        }
    }

    //
    override fun getEmployeeFromNetworkObservable(): Maybe<Either<ErrorDTO, List<EmployeeEntity>>> {

        return employeeAPI.getEmployeesObservable().map { responsesBody ->
            when (responsesBody.code()) {
                200 -> {
                    val employeeDTO =
                        EmployeeListDTOJsonAdapter(moshi).fromJson(responsesBody.body()!!.string())
                    Right(employeeMapper.transformToEntity(employeeDTO!!.employeeDto))
                }
                else -> {
                    val errorDTO =
                        ErrorDTOJsonAdapter(moshi).fromJson(responsesBody.errorBody()!!.string())!!
                    Left(errorDTO)
                }
            }
        }
    }

//    override fun saveEmployeeListToDatabase(employeeEntities: List<EmployeeEntity>): Maybe<List<Long>> {
//        return  employeeDAO.insertListOfEmployees(employeeEntities)
//    }

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

//   fun getEmployeeFromDB(): Flowable<List<EmployeeEntity>> {
//        return employeeDAO.getEmployees().doOnNext {
//            Log.d("Repo", it.size.toString())
//        }
//    }


}