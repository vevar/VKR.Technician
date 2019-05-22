package com.nstu.technician.feature.maintenance.job

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.maintenance.job.EndMaintenanceJobUseCase
import com.nstu.technician.domain.usecase.maintenance.job.GetMaintenanceJobUseCase
import com.nstu.technician.domain.usecase.maintenance.job.StartMaintenanceJobUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File

class MaintenanceJobVM(
    val maintenanceJobId: Long,
    private val getMaintenanceJobUseCase: GetMaintenanceJobUseCase,
    private val startMaintenanceJobUseCase: StartMaintenanceJobUseCase,
    private val endMaintenanceJobUseCase: EndMaintenanceJobUseCase
) : ViewModel() {

    companion object {
        const val TAG = "MaintenanceJobVM"
    }

    private val _mMaintenanceJob: MutableLiveData<MaintenanceJob> = MutableLiveData()
    val mMaintenanceJob: LiveData<MaintenanceJob>
        get() = _mMaintenanceJob
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

    fun startJob() {
        viewModelScope.launch {
            startMaintenanceJobUseCase.execute(
                object : CallUseCase<MaintenanceJob> {
                    override suspend fun onSuccess(result: MaintenanceJob) {
                        _mMaintenanceJob.value = result
                    }

                    override suspend fun onFailure(throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                },
                StartMaintenanceJobUseCase.Param.forMaintenanceJob(
                    _mMaintenanceJob.value ?: throw IllegalStateException("maintenance must be set")
                )
            )
        }
    }

    fun startJob(imageFile: File) {
        viewModelScope.launch {
            startMaintenanceJobUseCase.execute(
                object : CallUseCase<MaintenanceJob> {
                    override suspend fun onSuccess(result: MaintenanceJob) {
                        _mMaintenanceJob.value = result
                    }

                    override suspend fun onFailure(throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                },
                StartMaintenanceJobUseCase.Param.forMaintenanceJob(
                    _mMaintenanceJob.value ?: throw IllegalStateException("maintenance must be set"),
                    imageFile
                )
            )
        }
    }

    fun endJob() {
        viewModelScope.launch {
            endMaintenanceJobUseCase.execute(
                object : CallUseCase<MaintenanceJob> {
                    override suspend fun onSuccess(result: MaintenanceJob) {
                        _mMaintenanceJob.value = result
                    }

                    override suspend fun onFailure(throwable: Throwable) {
                        throwable.printStackTrace()
                    }

                },
                EndMaintenanceJobUseCase.Param.forMaintenanceJob(
                    _mMaintenanceJob.value ?: throw IllegalStateException("maintenance must be set")
                )
            )
        }
    }

    fun endJob(imageFile: File) {
        viewModelScope.launch {
            endMaintenanceJobUseCase.execute(
                object : CallUseCase<MaintenanceJob> {
                    override suspend fun onSuccess(result: MaintenanceJob) {
                        _mMaintenanceJob.value = result
                    }

                    override suspend fun onFailure(throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                },
                EndMaintenanceJobUseCase.Param.forMaintenanceJob(
                    _mMaintenanceJob.value ?: throw IllegalStateException("maintenance must be set"),
                    imageFile
                )
            )
        }
    }
}