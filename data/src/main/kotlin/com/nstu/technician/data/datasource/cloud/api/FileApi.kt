package com.nstu.technician.data.datasource.cloud.api

import com.nstu.technician.data.dto.common.ArtifactDTO
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface FileApi {
    @Multipart
    @POST("/api/file/upload")
    fun upload(@Query("description") description: String = "", @Part file: MultipartBody.Part): Call<ArtifactDTO>

    /*download by docscan id*/
    @Streaming
    @GET("/api/file/load")
    fun downLoad(@Query("id") id: Long): Call<ResponseBody>
}