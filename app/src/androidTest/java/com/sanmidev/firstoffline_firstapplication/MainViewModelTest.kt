package com.sanmidev.firstoffline_firstapplication

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import com.nhaarman.mockito_kotlin.verify
import com.sanmidev.firstoffline_firstapplication.data.entities.EmployeeListEntity
import com.sanmidev.firstoffline_firstapplication.data.mapper.EmployeeMapper
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeAPI
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeResult
import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepository
import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepositoryImpl
import com.sanmidev.firstoffline_firstapplication.utils.AppScheduler
import com.sanmidev.firstoffline_firstapplication.utils.TestAppScheduler
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(JUnit4::class)
class MainViewModelAndroidTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<EmployeeResult>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)


    }

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



    @Test
    fun getEmployees_EmployeesLIst_Should_returnSuccessReponse(){

        val ctx  = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext
        val input: InputStream = ctx.getResources().getAssets().open("employees.json")

        val fileContent = Utils.readTextStream(input)
        Log.d("main", fileContent)

        mockWebServer.enqueue(
            MockResponse()
                .setBody(fileContent)
                .setResponseCode(HttpURLConnection.HTTP_OK))

        val employeeEnittyList = moshi.adapter(EmployeeListEntity::class.java)
            .fromJson(fileContent)!!
//
        val mainViewModel = MainViewModel(repository, appScheduler)

      //  val liveDataResult =  mainViewModel.employeeMutableLiveData as LiveData<EmployeeResult>
        mainViewModel.getEmployees().observeForever(observer)


      verify(observer).onChanged(EmployeeResult.successReponse(employeeEnittyList.employee))




//        Assert.assertThat(value,
//            IsInstanceOf.instanceOf((EmployeeResult.successReponse::class.java)))
//        )
    }

}


fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

object Utils {
    @Throws(Exception::class)
    fun readTextStream(inputStream: InputStream): String {
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int = 0
        while (inputStream.read(buffer).also({ length = it }) != -1) {
            result.write(buffer, 0, length)
        }
        return result.toString("UTF-8")
    }
}