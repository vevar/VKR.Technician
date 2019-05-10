package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.tool.ComponentTypeEntity

@Dao
interface ComponentTypeDao {

    @Query("SELECT * FROM componenttypeentity WHERE oid=:id")
    fun findById(id: Long): ComponentTypeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(componentTypeEntity: ComponentTypeEntity): Long

    @Delete
    fun delete(componentTypeEntity: ComponentTypeEntity)

    @Query("SELECT * FROM componenttypeentity")
    fun findAll(): List<ComponentTypeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<ComponentTypeEntity>): List<Long>

    @Query("DELETE FROM componenttypeentity")
    fun deleteAll()
}