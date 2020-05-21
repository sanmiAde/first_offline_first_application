package com.sanmidev.firstoffline_firstapplication.data.mapper

import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeDTO
import com.sanmidev.firstoffline_firstapplication.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class EmployeeMapper @Inject constructor()  : BaseMapper<List<EmployeeEntity>, List<EmployeeDTO>> {

    override fun transformToDTO(type: List<EmployeeEntity>): List<EmployeeDTO> {
        return type.map { employeeEntity: EmployeeEntity ->
            EmployeeDTO(
                employeeEntity.age,
                employeeEntity.firstname,
                employeeEntity.gender,
                employeeEntity.id.toString(),
                employeeEntity.lastname,
                employeeEntity.nationality
            )

        }
    }

    override fun transformToEntity(type: List<EmployeeDTO>): List<EmployeeEntity> {
        return type.map { employeeDTO: EmployeeDTO ->
            EmployeeEntity(
                employeeDTO.age,
                employeeDTO.firstname,
                employeeDTO.gender,
                employeeDTO.id.toInt(),
                employeeDTO.lastname,
                employeeDTO.nationality
            )
        }
    }


}