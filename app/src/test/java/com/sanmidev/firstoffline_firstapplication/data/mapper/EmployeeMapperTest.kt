package com.sanmidev.firstoffline_firstapplication.data.mapper

import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeEntity
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeListEntity
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeDTO
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeListDTO
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import kotlin.random.Random


class EmployeeMapperTest {

    private lateinit var listOfEmployeeEntity: EmployeeListEntity
    private lateinit var listOfEmployeeListDTO: EmployeeListDTO


    @Before
    fun setUp() {

        val employeeDTOList = mutableListOf<EmployeeDTO>()
        val employeeEntityList = mutableListOf<EmployeeEntity>()

        (1..4).forEach {


            employeeDTOList.add(
                EmployeeDTO(
                    Random.nextInt().toString(),
                    Random.nextInt().toString(),
                    Random.nextInt().toString(),
                    Random.nextInt().toString(),
                    Random.nextInt().toString(),
                    Random.nextInt().toString()
                )
            )
            employeeEntityList.add(
                EmployeeEntity(
                    Random.nextInt().toString(),
                    Random.nextInt().toString(),
                    Random.nextInt().toString(),
                    Random.nextInt().toInt(),
                    Random.nextInt().toString(),
                    Random.nextInt().toString()
                )
            )

        }

        listOfEmployeeListDTO = EmployeeListDTO(employeeDTOList)
        listOfEmployeeEntity = EmployeeListEntity(employeeEntityList)

    }

    @Test
    fun transformToEntitiyListOfDtoShouldReturnCorrectData() {

        val listOfEmployeeEntity =
            EmployeeMapper().transformToEntity(listOfEmployeeListDTO.employeeDto)
        val convertedEntity = listOfEmployeeEntity[0]

        Assert.assertEquals(convertedEntity.age, listOfEmployeeEntity[0].age)
        Assert.assertEquals(convertedEntity.firstname, listOfEmployeeEntity[0].firstname)
        Assert.assertEquals(convertedEntity.gender, listOfEmployeeEntity[0].gender)
        Assert.assertEquals(convertedEntity.id, listOfEmployeeEntity[0].id)
        Assert.assertEquals(convertedEntity.lastname, listOfEmployeeEntity[0].lastname)
        Assert.assertEquals(convertedEntity.nationality, listOfEmployeeEntity[0].nationality)


    }

    @Test
    fun transformToEntitiyListOfDtoShouldCorrectType(){
        val listOfEmployeeEntity =
            EmployeeMapper().transformToEntity(listOfEmployeeListDTO.employeeDto)
        val convertedEntity = listOfEmployeeEntity[0]

        Assert.assertThat(convertedEntity, instanceOf(EmployeeEntity::class.java))
    }

    @Test
    fun transformToDtoListOFEntitesShouldReturnTheCorrectData() {

        val employeeListDTO = EmployeeMapper().transformToDTO(listOfEmployeeEntity.employee)
        val convertedDto = employeeListDTO[0]


        Assert.assertEquals(convertedDto.age, employeeListDTO [0].age)
        Assert.assertEquals(convertedDto.firstname, employeeListDTO [0].firstname)
        Assert.assertEquals(convertedDto.gender, employeeListDTO [0].gender)
        Assert.assertEquals(convertedDto.id, employeeListDTO [0].id)
        Assert.assertEquals(convertedDto.lastname, employeeListDTO [0].lastname)
        Assert.assertEquals(convertedDto.nationality, employeeListDTO [0].nationality)
    }


    @Test
    fun transformToDtoListOFEntitesShouldReturnTheCorrectType(){
        val employeeListDTO = EmployeeMapper().transformToDTO(listOfEmployeeEntity.employee)
        val convertedDto = employeeListDTO[0]

        Assert.assertThat(convertedDto, instanceOf(EmployeeDTO::class.java))
    }
}