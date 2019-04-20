package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.document.ContractorEntity

@Dao
interface ContractorDao {
    @Query("SELECT * FROM contractorentity WHERE oid=:id")
    fun findById(id: Long): ContractorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(contractorEntity: ContractorEntity)
}