package com.nstu.technician.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nstu.technician.feature.BaseActivity

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}