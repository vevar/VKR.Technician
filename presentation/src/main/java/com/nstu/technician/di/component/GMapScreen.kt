package com.nstu.technician.di.component

import com.nstu.technician.di.ScreenScope
import com.nstu.technician.feature.plan.jobs.map.GMapFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class, GMapComponent::class])
interface GMapScreen {
    fun inject(gMapFragment: GMapFragment)
}