package com.nstu.technician.di.component.list.maintenance

import com.nstu.technician.data.di.component.RepositoryComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.module.model.ListMaintenanceModule
import com.nstu.technician.feature.plan.jobs.list.maintenece.requests.ListMaintenanceForDayFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class, RepositoryComponent::class],
    modules = [ListMaintenanceModule::class])
interface ListMaintenanceScreen {

    fun inject(listMaintenanceForDayFragment: ListMaintenanceForDayFragment)
}