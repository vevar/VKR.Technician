package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.user.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM accountentity LIMIT 1")
    fun find(): AccountEntity?

    @Transaction
    fun save(account: AccountEntity) {
        nukeTable()
        insert(account)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: AccountEntity)

    @Query("DELETE FROM accountentity")
    fun nukeTable()
}