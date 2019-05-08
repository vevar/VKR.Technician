package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.job.JobTypeEntity

@Dao
interface JobTypeDao {

    @Query("SELECT * FROM jobtypeentity WHERE oid=:id")
    fun findById(id: Long): JobTypeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(jobTypeEntity: JobTypeEntity):Long

    @Delete
    fun delete(jobTypeEntity: JobTypeEntity)
}