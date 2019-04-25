package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.job.MaintenanceEntity

@Dao
interface MaintenanceDao {
    @Query("SELECT * FROM maintenanceentity WHERE oid=:id")
    fun findById(id: Long): MaintenanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(maintenanceEntity: MaintenanceEntity)

    @Query("SELECT * FROM maintenanceentity WHERE shift_id=:shiftId")
    fun findByIdShift(shiftId: Long): List<MaintenanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(listMaintenance: List<MaintenanceEntity>)
}