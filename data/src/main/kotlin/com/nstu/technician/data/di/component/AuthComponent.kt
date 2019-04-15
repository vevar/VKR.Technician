package com.nstu.technician.data.di.component

import com.nstu.technician.data.di.model.*
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import dagger.Component

@Component(
    modules = [ApiModule::class, DaoModule::class, DataSourceModule::class,
        RepositoryModule::class, InterceptorModule::class]
)
interface AuthComponent {

    fun userRepository(): UserRepository

    fun accountRepository(): AccountRepository

    fun technicianRepository(): TechnicianRepository

}