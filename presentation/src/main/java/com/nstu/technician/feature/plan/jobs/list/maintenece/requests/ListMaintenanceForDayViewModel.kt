package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.util.Log
import androidx.lifecycle.*
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.job.LoadListMaintenanceUseCase
import kotlinx.coroutines.*

class ListMaintenanceForDayViewModel(
    private val idShift: Long,
    private val loadListMaintenanceUseCase: LoadListMaintenanceUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "Maintenances_ViewModel"
    }

    private val _listMaintenance = MutableLiveData<List<Maintenance>>(listOf())
    val listMaintenance: LiveData<List<Maintenance>>
        get() = _listMaintenance
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _messageIdResource: MutableLiveData<Int> = MutableLiveData()
    val message: LiveData<Int>
        get() = _messageIdResource

    fun loadListMaintenance() {
        launchDataLoad {
            loadListMaintenanceUseCase.execute(object : CallUseCase<List<Maintenance>> {
                override suspend fun onSuccess(result: List<Maintenance>) {
                    _listMaintenance.value = result
                }

                override suspend fun onFailure(throwable: Throwable) {
                    Log.d(TAG, throwable.message)
                }
            }, LoadListMaintenanceUseCase.Param.forShift(idShift))
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d(TAG, "(${this@ListMaintenanceForDayViewModel})Loader is called")
                block()
            } finally {
                _isLoading.value = false
                Log.d(TAG, "(${this@ListMaintenanceForDayViewModel})finally is called")
            }
            Log.d(TAG, "(${this@ListMaintenanceForDayViewModel})launch is finished")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "$this is cleared")
    }
}