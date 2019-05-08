package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.tool.ComponentUnitEntity

@Dao
interface ComponentUnitDao {

    @Query("SELECT * FROM componentunitentity WHERE maintenance_job_id=:id")
    fun findByMaintenanceJob(id: Long): List<ComponentUnitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(componentUnitEntity: ComponentUnitEntity)

    @Delete
    fun delete(componentUnitEntity: ComponentUnitEntity)
}