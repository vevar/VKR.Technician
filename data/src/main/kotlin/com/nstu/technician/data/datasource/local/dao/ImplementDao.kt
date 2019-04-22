package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.tool.ImplementsEntity

@Dao
interface ImplementDao {

    @Query("SELECT * FROM implementsentity WHERE oid=:id")
    fun findById(id: Long): ImplementsEntity

    @Query("SELECT * FROM implementsentity WHERE maintenance_job_id=:maintenanceJobId")
    fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(implementsEntity: ImplementsEntity)

}