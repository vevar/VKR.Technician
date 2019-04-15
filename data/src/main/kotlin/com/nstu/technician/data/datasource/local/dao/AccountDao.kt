package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.domain.model.user.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account LIMIT 1")
    fun find(): Account?

    @Transaction
    fun save(account: Account) {
        nukeTable()
        insert(account)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Query("DELETE FROM user")
    fun nukeTable()
}