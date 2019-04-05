package com.nstu.technician.di.component

import com.nstu.technician.di.ScreenScope
import com.nstu.technician.feature.maintenance.MaintenanceFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class, MaintenanceComponent::class])
interface MaintenanceScreen {
    fun inject(maintenanceFragment: MaintenanceFragment)
}