package com.nstu.technician.data.repository

import android.util.Log
import com.nstu.technician.data.datasource.entity.CLOUD
import com.nstu.technician.data.datasource.entity.FileDataSource
import com.nstu.technician.data.until.toArtifact
import com.nstu.technician.domain.model.common.Artifact
import com.nstu.technician.domain.repository.FileRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class FileRepositoryImpl @Inject constructor(
    @Named(CLOUD)
    private val fileCloudSource: FileDataSource
) : FileRepository{
    companion object{
        private const val TAG ="FileRepositoryImpl"
    }

    override suspend fun save(file: File, mediaType: String): Artifact {
        return fileCloudSource.save(file,mediaType).apply {
            Log.d(TAG, this.systemFileName())
        }.toArtifact()
    }

    override suspend fun save(obj: File): File? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): File {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(obj: File) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}