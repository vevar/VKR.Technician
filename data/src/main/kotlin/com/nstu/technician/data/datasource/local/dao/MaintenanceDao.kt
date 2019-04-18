package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.job.MaintenanceEntity
import com.nstu.technician.data.dto.job.MaintenanceDTO

@Dao
interface MaintenanceDao {

    @Query("SELECT * FROM maintenanceentity WHERE shift_id=:shiftId")
    fun findByIdShift(shiftId: Long): List<MaintenanceEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(listMaintenance: List<MaintenanceEntity>)
}