package com.nstu.technician.di.component.plan.jobs

import com.nstu.technician.data.di.component.PlanJobsComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.module.model.PlanJobsModule
import com.nstu.technician.feature.plan.jobs.PlanJobsFragment
import dagger.Component

@ScreenScope
@Component(dependencies = [AppComponent::class, PlanJobsComponent::class],
    modules = [PlanJobsModule::class])
interface PlanJobsScreen {
    fun inject(planJobsFragment: PlanJobsFragment)
}