package com.sanmidev.firstoffline_firstapplication.data.remote

import io.reactivex.Maybe
import retrofit2.http.GET

interface EmployeeAPI{

    @GET("/5d1313a00e0000fd3fb4a1c4")
    fun getEmployees() : Maybe<EmployeeListDTO>
}