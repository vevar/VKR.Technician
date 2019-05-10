package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.tool.ComponentEntity

@Dao
interface ComponentDao {

    @Query("SELECT * FROM componententity WHERE oid=:id")
    fun findById(id: Long): ComponentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(componentEntity: ComponentEntity): Long

    @Delete
    fun delete(componentEntity: ComponentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<ComponentEntity>): List<Long>

    @Query("SELECT * FROM componententity")
    fun findAll(): List<ComponentEntity>

    @Query("DELETE FROM componententity")
    fun deleteAll()
}