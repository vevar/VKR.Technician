package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.common.Artifact
import java.io.File

interface FileRepository : CrudRepository<File, Long> {
    suspend fun save(file: File, mediaType: String): Artifact
}
