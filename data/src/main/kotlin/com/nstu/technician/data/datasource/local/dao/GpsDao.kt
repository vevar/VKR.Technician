package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.common.GPSEntity

@Dao
interface GpsDao {

    @Query("SELECT * FROM gpsentity WHERE oid=:id")
    fun findById(id: Long): GPSEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(gpsEntity: GPSEntity)
}
