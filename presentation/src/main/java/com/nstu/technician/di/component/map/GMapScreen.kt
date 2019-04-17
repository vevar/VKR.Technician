package com.nstu.technician.di.component.map

import com.nstu.technician.data.di.component.MapComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.module.model.MapModule
import com.nstu.technician.feature.plan.jobs.map.GMapFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class, MapComponent::class],
    modules = [MapModule::class])
interface GMapScreen {
    fun inject(gMapFragment: GMapFragment)
}