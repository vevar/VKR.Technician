package com.nstu.technician.feature.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelMessenger : ViewModel() {
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message
}