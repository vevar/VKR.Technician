package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.tool.ComponentEntity

@Dao
interface ComponentDao {

    @Query("SELECT * FROM componententity WHERE oid=:id")
    fun findById(id: Long): ComponentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(componentEntity: ComponentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(list: List<ComponentEntity>)
}