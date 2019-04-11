package com.nstu.technician.di.component

import com.nstu.technician.di.ScreenScope
import com.nstu.technician.feature.login.LoginActivity
import dagger.Component

@ScreenScope
@Component(dependencies = [LoginComponent::class, AppComponent::class])
interface LoginScreen {

    fun inject(loginActivity: LoginActivity)
}