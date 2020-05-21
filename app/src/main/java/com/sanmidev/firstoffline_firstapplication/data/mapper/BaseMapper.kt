package com.sanmidev.firstoffline_firstapplication.data.mapper

interface BaseMapper<E,D> {

    //Transforms a entity to a DTO
    fun transformToDTO(type: E): D

    //Transforms a DTO to entity
    fun transformToEntity(type: D): E
}