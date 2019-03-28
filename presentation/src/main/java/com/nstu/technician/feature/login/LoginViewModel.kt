package com.nstu.technician.feature.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxminyaev.ratingnstustudent.domain.usecase.auth.AuthUseCase
import com.nstu.technician.R
import com.nstu.technician.domain.model.Technician
import com.nstu.technician.feature.util.TAG_PRESENTATION
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context
) : ViewModel() {
    val username: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val message: MutableLiveData<String> = MutableLiveData()

    private val _technician: MutableLiveData<Technician> = MutableLiveData()
    val technician: LiveData<Technician>
        get() = _technician
    private val _isFieldsFilled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFieldsFilled: LiveData<Boolean>
        get() = _isFieldsFilled

    init {
        username.observeForever {
            _isFieldsFilled.value = it.isNotBlank() && password.value!!.isNotBlank()
        }
        password.observeForever {
            _isFieldsFilled.value = it.isNotBlank() && username.value!!.isNotBlank()
        }
    }

    fun singIn() {
        launchDataLoad {
            delay(1_000)
            message.value = context.getString(R.string.incorrect_data_of_account)
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