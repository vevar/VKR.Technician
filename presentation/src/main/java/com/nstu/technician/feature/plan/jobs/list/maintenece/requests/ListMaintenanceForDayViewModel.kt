package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListMaintenanceForDayViewModel : ViewModel() {

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
            delay(1_000)
            _listMaintenance.value = mutableListOf<Any>(1, 2, 4, 5, 6)
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } finally {
                _isLoading.value = false
            }
        }
    }

}