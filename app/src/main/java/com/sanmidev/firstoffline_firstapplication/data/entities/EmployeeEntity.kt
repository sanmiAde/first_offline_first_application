package com.sanmidev.firstoffline_firstapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "employee_table")
data class EmployeeEntity(

    val age: String,

    val firstname: String,

    val gender: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val lastname: String,

    val nationality: String
)