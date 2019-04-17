package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface UserApi {

    @POST("/api/user/login")
    fun login(@Body body: AccountDTO): Call<UserDTO>
}