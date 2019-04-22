package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.tool.ImplementUnitEntity

@Dao
interface ImplementUnitDao {

    @Query("SELECT * FROM implementunitentity WHERE oid=:id")
    fun findById(id: Long): ImplementUnitEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(implementUnitEntity: ImplementUnitEntity)
}