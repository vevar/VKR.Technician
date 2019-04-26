package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nstu.technician.data.database.entity.tool.ImplementsEntity

@Dao
interface ImplementDao {

    @Query("SELECT * FROM implementsentity WHERE oid=:id")
    fun findById(id: Long): ImplementsEntity?

    @Query("SELECT implementsentity.oid, implementsentity.name, implementsentity.currentNumber FROM implementsentity INNER JOIN maintenancejobimplementjoin ON implementsentity.oid=maintenancejobimplementjoin.implements_id WHERE maintenancejobimplementjoin.maintenace_job_id=:maintenanceJobId")
    fun findByMaintenanceJobId(maintenanceJobId: Long): List<ImplementsEntity>

    @Query("INSERT INTO maintenancejobimplementjoin(maintenace_job_id, implements_id) SELECT :maintenanceJobId, :implementId WHERE CASE WHEN NOT EXISTS (SELECT * FROM maintenancejobimplementjoin WHERE maintenace_job_id=:maintenanceJobId AND implements_id=:implementId) THEN 'TRUE' ELSE 'FALSE' END='TRUE'")
    fun saveForMaintenanceJob(maintenanceJobId: Long, implementId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(implementsEntity: ImplementsEntity)

    @Query("SELECT implementsentity.oid, implementsentity.name, implementsentity.currentNumber FROM implementsentity INNER JOIN jobtypeimplementsjoin ON implementsentity.oid = jobtypeimplementsjoin.implements_id WHERE jobtypeimplementsjoin.job_type_id=:jobTypeId")
    fun findByJobTypeId(jobTypeId: Long): List<ImplementsEntity>

    @Query("INSERT INTO jobtypeimplementsjoin(job_type_id, implements_id) SELECT :jobTypeId, :implementId WHERE CASE WHEN NOT EXISTS (SELECT * FROM jobtypeimplementsjoin WHERE job_type_id=:jobTypeId AND implements_id=:implementId) THEN 'TRUE' ELSE 'FALSE' END='TRUE'")
    fun saveForJobTypeId(jobTypeId: Long, implementId: Long)
}