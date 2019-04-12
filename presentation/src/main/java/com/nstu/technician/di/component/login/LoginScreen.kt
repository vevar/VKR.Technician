package com.nstu.technician.di.component.login

import com.nstu.technician.data.di.component.LoginComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.module.model.LoginModule
import com.nstu.technician.feature.login.LoginActivity
import dagger.Component

@ScreenScope
@Component(dependencies = [LoginComponent::class, AppComponent::class],
    modules = [LoginModule::class])
interface LoginScreen {

    fun inject(loginActivity: LoginActivity)
}