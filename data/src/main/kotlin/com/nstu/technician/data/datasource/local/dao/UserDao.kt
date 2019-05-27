package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.user.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity")
    fun find(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: UserEntity): Long

    @Query("DELETE FROM userentity")
    fun nukeTable()
}