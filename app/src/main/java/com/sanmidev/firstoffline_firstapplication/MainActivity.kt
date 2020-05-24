package com.sanmidev.firstoffline_firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeResult
import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepository
import com.sanmidev.firstoffline_firstapplication.utils.AppScheduler
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var employeeRepository: EmployeeRepository

    @Inject
    lateinit var appScheduler: AppScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        ((application) as MainApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getEmployees().observe(this, androidx.lifecycle.Observer {
            when(it){
                is EmployeeResult.successReponse -> {
                    progressBar.visibility = View.GONE
                    employee_response.visibility = View.VISIBLE
                    employee_response.text = it.data.toString()
                }
                is EmployeeResult.errorResponse -> {
                    progressBar.visibility = View.GONE
                    employee_response.visibility = View.VISIBLE
                    employee_response.text = it.errorDTO.toString()
                }

                is EmployeeResult.loading -> {
                    progressBar.visibility = View.VISIBLE
                    employee_response.visibility = View.GONE

                }
            }
        })

    }


    private fun getEmployees(): LiveData<EmployeeResult> {
        val employeeMutableLiveData = MutableLiveData<EmployeeResult>()
        employeeMutableLiveData.value = EmployeeResult.loading()

        employeeRepository.getEmployeeFromNetworkObservable()
            .subscribeOn(appScheduler.io())
            .observeOn(appScheduler.main()).subscribeBy(
                onComplete = {

                },
                onSuccess = { eitherResponse ->
                    eitherResponse.fold({
                        employeeMutableLiveData.value = EmployeeResult.errorResponse(it)
                        Log.d("main", it.error)
                    }, {
                        employeeMutableLiveData.value = EmployeeResult.successReponse(it)
                        Log.d("main", it.toString())
                    })
                },
                onError = {
                    Log.d("main", it.toString())
                }
            )

        return employeeMutableLiveData as LiveData<EmployeeResult>

    }
}
