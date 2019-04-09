package com.nstu.technician.feature.login

import com.nstu.technician.feature.util.BaseViewModelFactory

class LoginViewModelFactory : BaseViewModelFactory<LoginViewModel>(LoginViewModel::class.java){

    override fun createViewModel(): LoginViewModel {
        return LoginViewModel()
    }
}