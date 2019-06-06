package com.nstu.technician.di.component.login

import com.nstu.technician.data.di.component.AuthComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.feature.login.AutoLoginActivity
import dagger.Component

@ScreenScope
@Component(dependencies = [AuthComponent::class, AppComponent::class])
interface AutoLoginComponent {

    fun inject(autoLoginActivity: AutoLoginActivity)
}