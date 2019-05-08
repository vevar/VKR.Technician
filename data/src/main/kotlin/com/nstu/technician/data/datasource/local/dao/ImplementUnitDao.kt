package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.tool.ImplementUnitEntity

@Dao
interface ImplementUnitDao {

    @Query("SELECT * FROM implementunitentity WHERE oid=:id")
    fun findById(id: Long): ImplementUnitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(implementUnitEntity: ImplementUnitEntity)

    @Query("SELECT * FROM implementunitentity WHERE implements_id=:implementsId")
    fun findByImplementsId(implementsId: Long): List<ImplementUnitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<ImplementUnitEntity>)

    @Delete
    fun delete(implementUnitEntity: ImplementUnitEntity)
}