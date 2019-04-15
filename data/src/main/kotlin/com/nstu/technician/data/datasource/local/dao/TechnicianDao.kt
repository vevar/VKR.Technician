package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.domain.model.user.Technician

@Dao
interface TechnicianDao {
    @Query("SELECT * FROM technician WHERE user_id=:userId")
    fun findByUserId(userId: Int): Technician?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(technician: Technician)
}