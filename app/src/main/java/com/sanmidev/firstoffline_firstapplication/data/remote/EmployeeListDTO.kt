package com.sanmidev.firstoffline_firstapplication.data.remote


import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeListDTO(
    @Json(name = "employee")
    val employeeDto: List<EmployeeDTO>
)