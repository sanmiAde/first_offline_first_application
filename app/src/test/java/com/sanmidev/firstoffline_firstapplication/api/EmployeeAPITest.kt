package com.sanmidev.firstoffline_firstapplication.api

import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeAPI
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class EmployeeAPITest {
    @get:Rule
    val mockWebServer = MockWebServer()

    val testJson = """
        {

          "employee":[

            {

              "firstname": "Fox",

              "lastname": "William",

              "age": "24",

              "gender":"Male",

              "id":"1",

              "nationality":"Italian"

            },

            {

              "firstname": "Dawood",

              "lastname": "Tyson",

              "age": "25",

              "gender":"Male",

              "id":"2",

              "nationality":"Jamaican"

            },

            {

              "firstname": "Jaylen",

              "lastname": "Downes",

              "age": "22",

              "gender":"Female",

              "id":"3",

              "nationality":"Irish"

            },

            {

              "firstname": "Ayomide",

              "lastname": "Hart",

              "age": "23",

              "gender":"Male",

              "id":"4",

              "nationality":"Israeli"

            },

            {

              "firstname": "Destiny",

              "lastname": "Cortes",

              "age": "25",

              "gender":"Female",

              "id":"5",

              "nationality":"Indian"

            }

          ]

        }
    """.trimIndent()


    private  val moshi by lazy {

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
    fun getEmployeesEmitsListOfEmployees() {

        mockWebServer.enqueue(

            MockResponse()

                .setBody(testJson)

                .setResponseCode(200))

        val testObserver = employeeAPI.getEmployees().test()

        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        assertEquals("/5d1313a00e0000fd3fb4a1c4",
            mockWebServer.takeRequest().path)


    }

    @Test(expected =  java.lang.AssertionError::class)
    fun getEmployeesEmitsStatus400(){
        mockWebServer.enqueue(

            MockResponse()
                .setResponseCode(400))

        val testObserver = employeeAPI.getEmployees().test()

        testObserver.assertNoErrors()
    }
}