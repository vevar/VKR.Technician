package com.nstu.technician.di.component.maintenance.job

import com.nstu.technician.data.di.component.MaintenanceJobComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.module.model.MaintenanceJobModule
import com.nstu.technician.feature.maintenance.job.MaintenanceJobFragment
import dagger.Component

@ScreenScope
@Component(
    dependencies = [AppComponent::class, MaintenanceJobComponent::class],
    modules = [MaintenanceJobModule::class]
)
interface MaintenanceJobScreen {

    fun inject(maintenanceJobFragment: MaintenanceJobFragment)
}