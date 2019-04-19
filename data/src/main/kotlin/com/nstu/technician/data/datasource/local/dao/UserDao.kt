package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.user.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity LIMIT 1")
    fun find(): UserEntity?

    @Transaction
    fun save(user: UserEntity) {
        nukeTable()
        insert(user)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Query("DELETE FROM userentity")
    fun nukeTable()
}