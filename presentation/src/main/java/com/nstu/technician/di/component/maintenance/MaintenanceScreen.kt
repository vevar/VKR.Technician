package com.nstu.technician.di.component.maintenance

import com.nstu.technician.data.di.component.MaintenanceComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.module.model.MaintenanceModule
import com.nstu.technician.feature.maintenance.JobListFragment
import com.nstu.technician.feature.maintenance.MaintenanceFragment
import dagger.Component

@ScreenScope
@Component(
    dependencies = [AppComponent::class, MaintenanceComponent::class],
    modules = [MaintenanceModule::class]
)
interface MaintenanceScreen {
    fun inject(maintenanceFragment: MaintenanceFragment)
    fun inject(jobListFragment: JobListFragment)
}