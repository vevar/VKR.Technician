package com.nstu.technician.data.di.component

import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.network.retorfit.Client
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import dagger.Component

@Component(
    modules = [ApiModule::class, DaoModule::class, DataSourceModule::class,
        RepositoryModule::class]
)
interface LoginComponent {

    fun userRepository(): UserRepository

    fun accountRepository(): AccountRepository

    fun technicianRepository(): TechnicianRepository

    fun inject(client: Client)
}