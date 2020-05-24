package com.sanmidev.firstoffline_firstapplication.data.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorDTO(
    @Json(name = "error")
    val error: String
)