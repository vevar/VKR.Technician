package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import com.nstu.technician.data.database.entity.job.FacilityEntity

@Dao
interface FacilityDao {
    fun findById(id: Long): FacilityEntity?
    fun save(facilityEntity: FacilityEntity)
}
