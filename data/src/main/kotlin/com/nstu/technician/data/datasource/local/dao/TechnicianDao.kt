package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.user.TechnicianEntity

@Dao
interface TechnicianDao {
    @Query("SELECT * FROM technicianentity WHERE user_id=:userId")
    fun findByUserId(userId: Long): TechnicianEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(technician: TechnicianEntity)
}