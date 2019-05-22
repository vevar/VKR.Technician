package com.nstu.technician.di.component.problem

import com.nstu.technician.data.di.component.ProblemComponent
import com.nstu.technician.di.ScreenScope
import com.nstu.technician.di.component.AppComponent
import com.nstu.technician.di.module.model.MaintenanceJobModule
import com.nstu.technician.feature.problem.ProblemFragment
import dagger.Component

@ScreenScope
@Component(
    dependencies = [AppComponent::class, ProblemComponent::class],
    modules = [MaintenanceJobModule::class]
)
interface ProblemScreen {

    fun inject(problemFragment: ProblemFragment)
}