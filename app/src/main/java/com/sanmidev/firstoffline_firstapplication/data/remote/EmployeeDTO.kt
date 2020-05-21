package com.sanmidev.firstoffline_firstapplication.data.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeeDTO(
    @Json(name = "age")
    val age: String,
    @Json(name = "firstname")
    val firstname: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "lastname")
    val lastname: String,
    @Json(name = "nationality")
    val nationality: String
)