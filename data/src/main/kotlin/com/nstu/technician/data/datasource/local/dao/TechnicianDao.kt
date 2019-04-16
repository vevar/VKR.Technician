package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.dto.user.TechnicianDTO

@Dao
interface TechnicianDao {
    @Query("SELECT * FROM techniciandto WHERE user_id=:userId")
    fun findByUserId(userId: Long): TechnicianDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(technician: TechnicianDTO)
}