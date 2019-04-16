package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.dto.user.UserDTO

@Dao
interface UserDao {

    @Query("SELECT * FROM userdto LIMIT 1")
    fun find(): UserDTO?

    @Transaction
    fun save(user: UserDTO) {
        nukeTable()
        insert(user)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserDTO)

    @Query("DELETE FROM userdto")
    fun nukeTable()
}