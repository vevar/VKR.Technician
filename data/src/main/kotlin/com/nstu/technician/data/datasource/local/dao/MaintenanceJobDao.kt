package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.job.MaintenanceJobEntity

@Dao
interface MaintenanceJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(maintenanceEntity: MaintenanceJobEntity): Long

    @Query("SELECT * FROM maintenancejobentity WHERE oid=:id")
    fun findById(id: Long): MaintenanceJobEntity?

    @Query("SELECT * FROM maintenancejobentity WHERE maintenance_id=:maintenanceId")
    fun findByMaintenanceId(maintenanceId: Long): List<MaintenanceJobEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<MaintenanceJobEntity>): List<Long>

    @Delete
    fun delete(maintenanceJobEntity: MaintenanceJobEntity)
}