package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.datasource.local.dao.ArtifactDao
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.until.toArtifactDTO
import com.nstu.technician.data.until.toArtifactEntity
import javax.inject.Inject

class ArtifactLocalSource @Inject constructor(
    private val artifactDao: ArtifactDao
) : ArtifactDataSource {

    override suspend fun delete(obj: ArtifactDTO) {
        artifactDao.delete(obj.toArtifactEntity())
    }

    override suspend fun findById(id: Long): ArtifactDTO? {
        return artifactDao.findById(id).toArtifactDTO()
    }

    override suspend fun save(obj: ArtifactDTO):Long {
        return artifactDao.save(obj.toArtifactEntity())
    }
}