package com.nstu.technician.data.network.retorfit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        private const val END_POINT = "http://217.71.138.9:4567"
    }

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(END_POINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofit(): Retrofit {
        return retrofit
    }

    fun addClient(client: OkHttpClient) {
        retrofit = retrofit.newBuilder().client(client)
            .build()
    }
}