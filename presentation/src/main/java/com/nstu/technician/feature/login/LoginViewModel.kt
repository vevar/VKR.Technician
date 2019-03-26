package com.nstu.technician.feature.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxminyaev.ratingnstustudent.domain.usecase.auth.AuthUseCase
import com.nstu.technician.feature.util.TAG_PRESENTATION
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var username: String = ""
    var password: String = ""

    // TODO #QUESTION How to bind the isLoading with view?
    val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message




    fun singIn() {
        launchDataLoad {
        }
    }


    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (exception: AuthUseCase.StudentNotFoundException) {
                Log.d(TAG_PRESENTATION, exception.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}