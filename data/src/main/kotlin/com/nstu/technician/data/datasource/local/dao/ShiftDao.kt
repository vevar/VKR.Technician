package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.ShiftEntity

@Dao
interface ShiftDao {

    @Query("SELECT * FROM shiftentity WHERE oid=:id")
    fun findById(id: Long): ShiftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(shiftEntity: ShiftEntity): Long

    @Delete
    fun delete(shiftEntity: ShiftEntity)
}
