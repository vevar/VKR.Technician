package com.nstu.technician.di.module

import com.nstu.technician.data.datasource.cloud.api.UserApi
import com.nstu.technician.data.network.RetrofitProvide
import dagger.Module
import dagger.Provides

@Module
class ApiModule(
    private val retrofitProvide: RetrofitProvide
){

    @Provides
    fun provideUserApi(): UserApi{
        return retrofitProvide.createUserApi()
    }
}