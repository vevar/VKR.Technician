package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class ListMaintenanceForDayViewModel : ViewModel() {
    companion object {
        private const val TAG = "Maintenances_ViewModel"
    }

    val listMaintenance: LiveData<List<Any>>
        get() = _listMaintenance
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message
    private val _listMaintenance = MutableLiveData<List<Any>>()


    fun loadListMaintenance() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                delay(500)
            }
            _listMaintenance.value = mutableListOf<Any>(1, 2, 4, 5, 6)
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