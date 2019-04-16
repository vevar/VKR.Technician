package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.dto.user.AccountDTO

@Dao
interface AccountDao {
    @Query("SELECT * FROM accountdto LIMIT 1")
    fun find(): AccountDTO?

    @Transaction
    fun save(account: AccountDTO) {
        nukeTable()
        insert(account)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: AccountDTO)

    @Query("DELETE FROM userdto")
    fun nukeTable()
}