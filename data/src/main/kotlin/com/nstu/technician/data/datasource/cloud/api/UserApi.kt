package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.model.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface UserApi {

    @POST("/api/user/login")
    fun login(@Body body: Account): Call<User>
}