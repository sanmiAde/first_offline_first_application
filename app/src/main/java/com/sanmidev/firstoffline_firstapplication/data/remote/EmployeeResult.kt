package com.sanmidev.firstoffline_firstapplication.data.remote

import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity

sealed class EmployeeResult {

    class successReponse(val data : List<EmployeeEntity>) : EmployeeResult()

    class errorResponse(val errorDTO: ErrorDTO) : EmployeeResult()

    class loading() : EmployeeResult()
}