package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.auth.AuthUseCase
import com.nstu.technician.feature.login.LoginViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideLoginViewModelFactory(authUseCase: AuthUseCase): BaseViewModelFactory<LoginViewModel>{
        return BaseViewModelFactory(LoginViewModel::class.java){
            LoginViewModel(authUseCase)
        }
    }
}