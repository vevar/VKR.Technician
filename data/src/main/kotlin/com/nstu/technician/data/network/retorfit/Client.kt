package com.nstu.technician.data.network.retorfit

import com.nstu.technician.data.di.component.DaggerLoginComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.network.interceptor.AccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class Client private constructor() {

    @Inject
    lateinit var accessTokenInterceptor: AccessTokenInterceptor

    fun buildRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(accessTokenInterceptor)
            .build()

        val retrofit = RetrofitBuilder.builder()
            .client(okHttpClient)
            .build()

        DaggerLoginComponent.builder()
            .apiModule(ApiModule(RetrofitBuilder.builder()))
            .dataSourceModule(DataSourceModule())
            .repositoryModule(RepositoryModule())
            .build().inject(this)
        return retrofit
    }
}