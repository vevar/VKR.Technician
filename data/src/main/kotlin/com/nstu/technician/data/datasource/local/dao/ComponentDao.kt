package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.tool.ComponentEntity

@Dao
interface ComponentDao {

    @Query("SELECT * FROM componententity WHERE oid=:id")
    fun findById(id: Long): ComponentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(componentEntity: ComponentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<ComponentEntity>): List<Long>

    @Delete
    fun delete(componentEntity: ComponentEntity)
}