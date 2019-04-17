package com.nstu.technician.data.di

import com.nstu.technician.data.client.NetworkClientTest
import com.nstu.technician.data.di.model.*
import com.nstu.technician.data.di.module.OnlyCloudDataSource
import com.nstu.technician.domain.repository.AccountRepository
import com.nstu.technician.domain.repository.TechnicianRepository
import com.nstu.technician.domain.repository.UserRepository
import dagger.Component

@Component(
    modules = [ApiModule::class, OnlyCloudDataSource::class,
        RepositoryModule::class, InterceptorModule::class]
)
interface AuthComponentTest {

    fun userRepository(): UserRepository

    fun accountRepository(): AccountRepository

    fun technicianRepository(): TechnicianRepository

    fun inject(networkClient: NetworkClientTest)

}