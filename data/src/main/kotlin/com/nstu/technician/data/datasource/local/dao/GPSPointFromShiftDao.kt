package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.job.GPSPointFromShiftEntity

@Dao
interface GPSPointFromShiftDao {

    @Query("SELECT * FROM gpspointfromshiftentity WHERE shift_id=:shiftId")
    fun findByShiftId(shiftId: Long): List<GPSPointFromShiftEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(gpsPointFromShiftEntity: GPSPointFromShiftEntity)

}