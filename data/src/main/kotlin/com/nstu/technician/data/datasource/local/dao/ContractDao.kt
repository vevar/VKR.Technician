package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.document.ContractEntity

@Dao
interface ContractDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(contractEntity: ContractEntity)

    @Query("SELECT * FROM contractentity WHERE oid=:id")
    fun findById(id: Long): ContractEntity?
}