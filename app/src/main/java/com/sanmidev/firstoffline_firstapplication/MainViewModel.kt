package com.sanmidev.firstoffline_firstapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeResult
import com.sanmidev.firstoffline_firstapplication.data.remote.repo.EmployeeRepository
import com.sanmidev.firstoffline_firstapplication.utils.AppScheduler
import com.sanmidev.firstoffline_firstapplication.utils.Scheduler
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainViewModel (private val repository: EmployeeRepository, private val appScheduler: Scheduler) : ViewModel() {
    val employeeMutableLiveData = MutableLiveData<EmployeeResult>()

    class VMFactory @Inject constructor(private val repository: EmployeeRepository, private val appScheduler: AppScheduler) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return  MainViewModel(repository, appScheduler) as T
        }

    }

    fun getEmployees(): LiveData<EmployeeResult> {

        employeeMutableLiveData.value = EmployeeResult.loading()

        repository.getEmployeeFromNetworkObservable()
            .subscribeOn(appScheduler.io())
            .observeOn(appScheduler.main()).subscribeBy(
                onComplete = {

                },
                onSuccess = { eitherResponse ->
                    eitherResponse.fold({
                        employeeMutableLiveData.value = EmployeeResult.errorResponse(it)

                    }, {
                        employeeMutableLiveData.value = EmployeeResult.successReponse(it)

                    })
                },
                onError = {

                }
            )

        return employeeMutableLiveData as LiveData<EmployeeResult>

    }
}