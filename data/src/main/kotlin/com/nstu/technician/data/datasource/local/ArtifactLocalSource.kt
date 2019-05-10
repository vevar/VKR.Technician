package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.ArtifactDataSource
import com.nstu.technician.data.datasource.local.dao.ArtifactDao
import com.nstu.technician.data.dto.common.ArtifactDTO
import com.nstu.technician.data.until.toArtifactDTO
import com.nstu.technician.data.until.toArtifactEntity
import com.nstu.technician.domain.exceptions.NotFoundException
import javax.inject.Inject

class ArtifactLocalSource @Inject constructor(
    private val artifactDao: ArtifactDao
) : ArtifactDataSource {

    companion object {
        private const val TAG = "ArtifactLocalSource"
    }

    override suspend fun delete(obj: ArtifactDTO) {
        artifactDao.delete(obj.toArtifactEntity())
    }

    override suspend fun findById(id: Long): ArtifactDTO {
        return artifactDao.findById(id)?.toArtifactDTO() ?: throw NotFoundException(TAG, "artifactDTO by id($id)")
    }

    override suspend fun save(obj: ArtifactDTO): Long {
        return artifactDao.save(obj.toArtifactEntity())
    }
}