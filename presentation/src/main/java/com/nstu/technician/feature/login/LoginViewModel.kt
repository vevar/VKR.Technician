package com.nstu.technician.feature.login

import android.util.Log
import androidx.lifecycle.*
import com.nstu.technician.R
import com.nstu.technician.domain.model.user.Technician
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.auth.AuthUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {
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
            if (username.value != null && password.value != password.value) {
                authUseCase.execute(object : CallUseCase<Technician> {
                    override suspend fun onSuccess(result: Technician) {
                        _technician.value = result
                    }

                    override suspend fun onFailure(throwable: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }, AuthUseCase.Param.forAuth(username.value!!, password.value!!))
            }

            messageIdResource.value = R.string.lbl_incorrect_data_of_account
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (throwable: Throwable) {
                Log.d(TAG, throwable.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}