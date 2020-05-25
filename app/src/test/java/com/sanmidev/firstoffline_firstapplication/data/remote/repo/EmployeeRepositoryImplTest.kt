package com.sanmidev.firstoffline_firstapplication.data.remote.repo


import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeListEntity
import com.sanmidev.firstoffline_firstapplication.data.mapper.EmployeeMapper
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeAPI
import com.sanmidev.firstoffline_firstapplication.data.remote.ErrorDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection


class EmployeeRepositoryImplTest {

    @get:Rule
    val mockWebServer = MockWebServer()


    private val employeeMapper = EmployeeMapper()

    private val repository: EmployeeRepository by lazy {

        EmployeeRepositoryImpl(employeeAPI, employeeMapper, moshi)
    }


    private val moshi by lazy {

        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    }
    private val retrofit by lazy {


        Retrofit.Builder()
// 1
            .baseUrl(mockWebServer.url("/"))
// 2
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
// 3
            .addConverterFactory(MoshiConverterFactory.create(moshi))
// 4
            .build()
    }

    private val employeeAPI by lazy {
        retrofit.create(EmployeeAPI::class.java)
    }

    @Test
    fun getEmployeeFromNetworkObservable_shouldReturnEmployees_whenRequestIsSuccessful() {
        val fileContent = javaClass.getResource("/employees.json")?.readText()!!

        val employeeEnittyList = moshi.adapter(EmployeeListEntity::class.java)
            .fromJson(fileContent)!!


        mockWebServer.enqueue(
            MockResponse()
                .setBody(fileContent)
                .setResponseCode(HttpURLConnection.HTTP_OK))



        val testObserver = repository.getEmployeeFromNetworkObservable().test()

         testObserver.values()[0].fold({}, {
           Assert.assertEquals(employeeEnittyList.employee, it)
        })

    }


    @Test
    fun getEmployeeFromNetworkObservable_shouldReturnError400_whenRequestIsNotSuccessful() {
        val fileContent = javaClass.getResource("/error.json")?.readText()!!
        val errorResponse = moshi.adapter(ErrorDTO::class.java).fromJson(fileContent)
        mockWebServer.enqueue(
            MockResponse()
                .setBody(fileContent)
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        )

        val testObserver = repository.getEmployeeFromNetworkObservable().test()
        testObserver.values()[0].fold({
            Assert.assertEquals(errorResponse, it)
            Assert.assertEquals(errorResponse?.error, it.error)
        }, {})

    }
}