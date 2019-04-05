package com.nstu.technician.feature.login

import android.util.Log
import androidx.lifecycle.*
import com.alxminyaev.ratingnstustudent.domain.usecase.auth.AuthUseCase
import com.nstu.technician.R
import com.nstu.technician.domain.model.Technician
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    companion object {
        private const val TAG = "LoginViewModel"
    }

    val username: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val messageIdResource: MutableLiveData<Int?> = MutableLiveData(null)
    private val _technician: MutableLiveData<Technician> = MutableLiveData()
    val technician: LiveData<Technician>
        get() = _technician
    val isFieldsFilled: LiveData<Boolean> = MediatorLiveData<Boolean>()
        .apply {
            addSource(username) {
                this.value = it.isNotBlank() && password.value!!.isNotBlank()
            }
            addSource(password) {
                this.value = it.isNotBlank() && username.value!!.isNotBlank()
            }
        }

    fun singIn() {
        launchDataLoad {
            delay(1_000)
            messageIdResource.value = R.string.incorrect_data_of_account
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (exception: AuthUseCase.StudentNotFoundException) {
                Log.d(TAG, exception.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}