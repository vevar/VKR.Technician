package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.document.ContractEntity

@Dao
interface ContractDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(contractEntity: ContractEntity): Long

    @Query("SELECT * FROM contractentity WHERE oid=:id")
    fun findById(id: Long): ContractEntity?

    @Delete
    fun delete(contractEntity: ContractEntity)
}