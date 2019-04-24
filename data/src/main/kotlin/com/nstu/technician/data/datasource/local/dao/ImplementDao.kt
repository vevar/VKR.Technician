package com.nstu.technician.data.datasource.local.dao

import androidx.room.*
import com.nstu.technician.data.database.entity.tool.ImplementsEntity
import com.nstu.technician.data.database.entity.tool.JobTypeImplementsJoin

@Dao
interface ImplementDao {

    @Query("SELECT * FROM implementsentity WHERE oid=:id")
    fun findById(id: Long): ImplementsEntity?

    @Query("SELECT * FROM implementsentity WHERE maintenance_job_id=:maintenanceJobId")
    fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(implementsEntity: ImplementsEntity)

    @Query("SELECT * FROM implementsentity INNER JOIN jobtypeimplementsjoin ON implementsentity.oid =jobtypeimplementsjoin.implements_id WHERE jobtypeimplementsjoin.job_type_id=:jobTypeId")
    fun findByJobTypeId(jobTypeId: Long): List<ImplementsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForJobTypeId(jobTypeImplementsJoin: JobTypeImplementsJoin)


}