package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.job.FacilityEntity

@Dao
interface FacilityDao {

    @Query("SELECT * FROM facilityentity WHERE oid=:id")
    fun findById(id: Long): FacilityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(facilityEntity: FacilityEntity)

    @Delete
    fun delete(facilityEntity: FacilityEntity)
}
