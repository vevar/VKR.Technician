package com.nstu.technician.feature.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoaderVM : ViewModel() {

    companion object {
        private const val TAG = "LoaderVM"
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d(TAG, "Loader is called")
                block()
            } finally {
                _isLoading.value = false
                Log.d(TAG, "finally of Loader is called")
            }
            Log.d(TAG, "launch is finished")
        }
    }
}