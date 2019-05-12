package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.BODY_MUST_BE_SET
import com.nstu.technician.data.datasource.cloud.api.FileApi
import com.nstu.technician.data.datasource.entity.FileDataSource
import com.nstu.technician.data.dto.common.ArtifactDTO
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class FileCloudSource @Inject constructor(
    private val fileApi: FileApi
) : FileDataSource {

    companion object {
        private const val TAG = "FileCloudSource"
    }

    override suspend fun findFileByArtifact(artifactDTO: ArtifactDTO): InputStream {
        val responseBody =
            fileApi.downLoad(artifactDTO.oid).execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
        return responseBody.byteStream()
    }

    override suspend fun save(file: File, mediaType: String): ArtifactDTO {
        val requestBody = RequestBody.create(MediaType.parse(mediaType), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)
        return fileApi.upload(file = filePart).execute().body() ?: throw IllegalStateException(BODY_MUST_BE_SET)
    }

    override suspend fun deleteByArtifact(artifactDTO: ArtifactDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}