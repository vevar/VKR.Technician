package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.domain.model.user.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun find(): User?

    @Transaction
    fun save(user: User) {
        nukeTable()
        insert(user)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM user")
    fun nukeTable()
}