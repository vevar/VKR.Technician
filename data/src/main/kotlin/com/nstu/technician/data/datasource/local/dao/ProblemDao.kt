package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.ProblemEntity

@Dao
interface ProblemDao {
    @Query("SELECT * FROM problementity WHERE oid=:id")
    fun findById(id: Long): ProblemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(problemEntity: ProblemEntity)
}