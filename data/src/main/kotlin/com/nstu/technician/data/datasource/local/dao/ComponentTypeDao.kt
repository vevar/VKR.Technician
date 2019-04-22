package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.tool.ComponentTypeEntity

@Dao
interface ComponentTypeDao {

    @Query("SELECT * FROM componenttypeentity WHERE oid=:id")
    fun findById(id: Long): ComponentTypeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(componentTypeEntity: ComponentTypeEntity)
}