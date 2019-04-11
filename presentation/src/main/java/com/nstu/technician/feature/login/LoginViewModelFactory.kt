package com.nstu.technician.feature.login

import com.nstu.technician.domain.usecase.auth.AuthUseCase
import com.nstu.technician.feature.util.BaseViewModelFactory
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val authUseCase: AuthUseCase
) : BaseViewModelFactory<LoginViewModel>(LoginViewModel::class.java){

    override fun createViewModel(): LoginViewModel {
        return LoginViewModel(authUseCase)
    }
}