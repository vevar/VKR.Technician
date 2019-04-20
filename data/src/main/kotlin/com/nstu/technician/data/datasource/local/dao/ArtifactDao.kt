package com.nstu.technician.data.datasource.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.common.ArtifactEntity

interface ArtifactDao {

    @Query("SELECT * FROM artifactentity")
    fun findById(id: Long): ArtifactEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(artifactEntity: ArtifactEntity)
}