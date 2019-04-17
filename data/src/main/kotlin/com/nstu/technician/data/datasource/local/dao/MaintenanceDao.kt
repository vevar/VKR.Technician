package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nstu.technician.data.dto.job.MaintenanceDTO

@Dao
interface MaintenanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(listMaintenance: List<MaintenanceDTO>)
}