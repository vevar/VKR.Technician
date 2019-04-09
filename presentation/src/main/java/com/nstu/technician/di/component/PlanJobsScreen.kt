package com.nstu.technician.di.component

import com.nstu.technician.di.ScreenScope
import com.nstu.technician.feature.plan.jobs.PlanJobsFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class, PlanJobsComponent::class])
interface PlanJobsScreen {
    fun inject(planJobsFragment: PlanJobsFragment)
}