package com.nstu.technician.data.datasource.cloud

import com.nstu.technician.data.datasource.cloud.api.ArtifactApi
import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.dto.common.ArtifactDTO
import javax.inject.Inject

class ArtifactCloudSource @Inject constructor(
    private val artifactApi: ArtifactApi
): ArtifactDataSource {

    override suspend fun findById(id: Long): ArtifactDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: ArtifactDTO): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: ArtifactDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}