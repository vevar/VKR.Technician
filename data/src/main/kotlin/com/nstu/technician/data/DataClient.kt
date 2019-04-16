package com.nstu.technician.data

import android.content.Context
import androidx.room.Room
import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.di.component.AuthComponent
import com.nstu.technician.data.di.component.DaggerAuthComponent
import com.nstu.technician.data.di.component.DaggerPlanJobsComponent
import com.nstu.technician.data.di.component.PlanJobsComponent
import com.nstu.technician.data.di.model.ApiModule
import com.nstu.technician.data.di.model.DaoModule
import com.nstu.technician.data.di.model.DataSourceModule
import com.nstu.technician.data.di.model.RepositoryModule
import com.nstu.technician.data.network.interceptor.AccessTokenInterceptor
import com.nstu.technician.data.network.interceptor.HandlerExceptionsInterceptor
import com.nstu.technician.data.network.retorfit.ApiProvider
import com.nstu.technician.data.network.retorfit.RetrofitProvider
import okhttp3.OkHttpClient
import javax.inject.Inject

class DataClient private constructor() {

    private lateinit var authComponent: AuthComponent
    private var retrofitProvider: RetrofitProvider = RetrofitProvider()
    private lateinit var appDataBase: AppDataBase

    @Inject
    lateinit var accessTokenInterceptor: AccessTokenInterceptor

    companion object {
        fun initDataClient(context: Context): DataClient {
            val dataClient = DataClient()
            dataClient.appDataBase =
                Room.databaseBuilder(context, AppDataBase::class.java, AppDataBase.DATABASE_NAME)
                    .build()

            dataClient.setupInjection()

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(dataClient.accessTokenInterceptor)
                .addInterceptor(HandlerExceptionsInterceptor())
                .build()

            dataClient.retrofitProvider.addClient(okHttpClient)

            return dataClient
        }
    }

    private fun setupInjection() {
        authComponent = DaggerAuthComponent.builder()
            .apiModule(ApiModule(ApiProvider(retrofitProvider)))
            .daoModule(DaoModule(appDataBase))
            .dataSourceModule(DataSourceModule())
            .repositoryModule(RepositoryModule())
            .build()
        authComponent.inject(this)
    }

    fun getAuthComponent(): AuthComponent {
        return authComponent
    }

    fun createPlanJobsComponent(): PlanJobsComponent {
        return DaggerPlanJobsComponent.builder()
            .apiModule(ApiModule(ApiProvider(retrofitProvider)))
            .dataSourceModule(DataSourceModule())
            .repositoryModule(RepositoryModule())
            .build()
    }
}