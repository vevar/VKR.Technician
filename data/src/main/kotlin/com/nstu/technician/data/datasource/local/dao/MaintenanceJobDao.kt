package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.job.MaintenanceJobEntity

@Dao
interface MaintenanceJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(maintenanceEntity: MaintenanceJobEntity)

    @Query("SELECT * FROM maintenancejobentity WHERE oid=:id")
    fun findById(id: Long): MaintenanceJobEntity
}