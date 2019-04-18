package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.ShiftEntity

@Dao
interface ShiftDao {

    @Query("SELECT * FROM shiftentity WHERE oid=:id")
    fun findById(id: Long): ShiftEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(shiftEntity: ShiftEntity)
}
