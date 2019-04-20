package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ArtifactDataSource
import com.nstu.technician.data.datasource.local.dao.ArtifactDao
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.until.convertToArtifactDTO
import com.nstu.technician.data.until.convertToArtifactEntitty
import javax.inject.Inject

class ArtifactLocalSource @Inject constructor(
    private val artifactDao: ArtifactDao
) : ArtifactDataSource {
    override suspend fun findById(id: Long): ArtifactDTO? {
        return artifactDao.findById(id).convertToArtifactDTO()
    }

    override suspend fun save(obj: ArtifactDTO) {
        artifactDao.save(obj.convertToArtifactEntitty())
    }
}