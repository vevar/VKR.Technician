package com.nstu.technician.device.di

import com.nstu.technician.data.di.component.RepositoryComponent
import com.nstu.technician.device.service.LocationMonitoringServiceImpl
import dagger.Component

@Component(dependencies = [RepositoryComponent::class])
interface LocationMonitoringServiceComponent {

    fun inject(locationMonitoringServiceImpl: LocationMonitoringServiceImpl)
}