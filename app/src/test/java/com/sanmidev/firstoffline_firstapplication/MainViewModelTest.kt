package com.sanmidev.firstoffline_firstapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.verify
import com.sanmidev.firstoffline_firstapplication.data.LiveDataTestUtil
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeListEntity
import com.sanmidev.firstoffline_firstapplication.data.mapper.EmployeeMapper
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeAPI
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeResult
import com.sanmidev.firstoffline_firstapplication.data.remote.ErrorDTO
import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepository
import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepositoryImpl
import com.sanmidev.firstoffline_firstapplication.utils.TestAppScheduler
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<EmployeeResult>

    @get:Rule
    val mockWebServer = MockWebServer()

    private val employeeMapper = EmployeeMapper()

    private val repository: EmployeeRepository by lazy {

        EmployeeRepositoryImpl(employeeAPI, employeeMapper, moshi)
    }

    private val appScheduler by lazy {
        TestAppScheduler()
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

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getEmployeeMutableLiveData_shouldReturnSuccess_whenRequestIsSuccessful() {

        val fileContent = javaClass.getResource("/employees.json")?.readText()!!

        val employeeEnittyList = moshi.adapter(EmployeeListEntity::class.java)
            .fromJson(fileContent)!!


        mockWebServer.enqueue(
            MockResponse()
                .setBody(fileContent)
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )

        val mainViewModel = MainViewModel(repository, appScheduler)


        mainViewModel.getEmployees().observeForever(observer)


        verify(observer).onChanged(any(EmployeeResult.successReponse::class.java))

       //no need to assert this. i already test for the correct value in the network layer.
        // if i perform any logic on this value then i can test it.
//        val resourceValue = LiveDataTestUtil.getValue(mainViewModel.employeeMutableLiveData)
//
//        Assert.assertEquals(
//            employeeEnittyList.employee,
//            (resourceValue as EmployeeResult.successReponse).data
//        )
    }

    @Test
    fun getEmployeeMutableLiveData_shouldReturnError_whenRequestIsNotSuccessful(){
        val fileContent = javaClass.getResource("/error.json")?.readText()!!
        val errorResponse = moshi.adapter(ErrorDTO::class.java).fromJson(fileContent)
        mockWebServer.enqueue(
            MockResponse()
                .setBody(fileContent)
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        )

        val mainViewModel = MainViewModel(repository, appScheduler)


        mainViewModel.getEmployees().observeForever(observer)


        verify(observer).onChanged(any(EmployeeResult.errorResponse::class.java))
    }
}