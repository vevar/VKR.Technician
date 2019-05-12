package com.nstu.technician.data.datasource.entity

import com.nstu.technician.data.dto.common.ArtifactDTO
import java.io.File
import java.io.InputStream

interface FileDataSource {
    suspend fun findFileByArtifact(artifactDTO: ArtifactDTO): InputStream

    suspend fun save(file: File, mediaType: String): ArtifactDTO

    suspend fun deleteByArtifact(artifactDTO: ArtifactDTO)
}