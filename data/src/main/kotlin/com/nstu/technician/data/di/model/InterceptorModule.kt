package com.nstu.technician.data.di.model

import com.nstu.technician.data.network.token.AccessTokenProvider
import com.nstu.technician.data.network.token.SessionTokenProvider
import dagger.Module
import dagger.Provides

@Module
class InterceptorModule {

    @Provides
    fun provideSessionTokenProvider(sessionTokenProvider: SessionTokenProvider): AccessTokenProvider {
        return sessionTokenProvider
    }
}