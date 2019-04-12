package com.nstu.technician.data.network.retorfit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder private constructor() {

    companion object {
        private const val END_POINT = "http://217.71.138.9:4567"

        fun builder(): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
        }
    }
}