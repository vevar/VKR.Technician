package com.nstu.technician.feature.maintenance.job

import android.util.Log
import androidx.lifecycle.*
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.maintenance.GetMaintenanceJobUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MaintenanceJobVM(
    private val maintenanceJobId: Long,
    maintenanceJob: MaintenanceJob?,
    private val getMaintenanceJobUseCase: GetMaintenanceJobUseCase
) : ViewModel() {

    constructor(maintenanceJob: MaintenanceJob, getMaintenanceJobUseCase: GetMaintenanceJobUseCase)
            : this(maintenanceJob.oid, maintenanceJob, getMaintenanceJobUseCase)

    constructor(maintenanceJobId: Long, getMaintenanceJobUseCase: GetMaintenanceJobUseCase)
            : this(maintenanceJobId, null, getMaintenanceJobUseCase)

    companion object {
        const val TAG = "MaintenanceJobVM"
    }

    private val _mMaintenanceJob: MutableLiveData<MaintenanceJob?> = MutableLiveData(maintenanceJob)
    val mMaintenanceJob: LiveData<MaintenanceJob> = MediatorLiveData<MaintenanceJob>()
        .apply {
            addSource(_mMaintenanceJob) {
                it != null
            }
        }
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadMaintenanceJob() {
        launchDataLoad {
            getMaintenanceJobUseCase.execute(object : CallUseCase<MaintenanceJob> {
                override suspend fun onSuccess(result: MaintenanceJob) {
                    _mMaintenanceJob.value = result
                }

                override suspend fun onFailure(throwable: Throwable) {
                    Log.d(TAG, throwable.stackTrace.toString())
                }

            }, GetMaintenanceJobUseCase.Param.forMaintenanceJob(maintenanceJobId))
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d(TAG, "(${this@MaintenanceJobVM})Loader is called")
                block()
            } finally {
                _isLoading.value = false
                Log.d(TAG, "(${this@MaintenanceJobVM})finally is called")
            }
            Log.d(TAG, "(${this@MaintenanceJobVM})launch is finished")
        }
    }
}