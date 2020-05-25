package com.sanmidev.firstoffline_firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
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
    lateinit var vmFactory: MainViewModel.VMFactory

    private val viewModel by lazy {
        ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
    }

    @Inject
    lateinit var appScheduler: AppScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        ((application) as MainApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getEmployees().observe(this, androidx.lifecycle.Observer {
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



}
