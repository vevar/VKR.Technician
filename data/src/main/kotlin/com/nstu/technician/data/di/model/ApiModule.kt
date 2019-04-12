package com.nstu.technician.data.di.model

import com.nstu.technician.data.datasource.cloud.api.TechnicianApi
import com.nstu.technician.data.datasource.cloud.api.UserApi
import dagger.Module
import dagger.Provides

@Module
class ApiModule(
    private val retrofitProvide: DataClient.RetrofitProvider
){

    @Provides
    fun provideUserApi(): UserApi{
        return retrofitProvide.createUserApi()
    }

    @Provides
    fun provideTechnicianApi(): TechnicianApi{
        return retrofitProvide.createTechnicianApi()
    }
}