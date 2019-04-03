package com.nstu.technician.di.component

import com.nstu.technician.di.ScreenScope
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class, ListMaintenanceComponent::class])
interface ListMaintenanceScreen {

    fun inject(listMaintenanceForDayFragment: ListMaintenanceForDayFragment)
}