package com.nstu.technician.data

import androidx.room.Database
import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.database.entity.ShiftEntity
import com.nstu.technician.data.database.entity.common.AddressEntity
import com.nstu.technician.data.database.entity.common.ArtifactEntity
import com.nstu.technician.data.database.entity.common.GPSEntity
import com.nstu.technician.data.database.entity.document.ContractEntity
import com.nstu.technician.data.database.entity.document.ContractorEntity
import com.nstu.technician.data.database.entity.job.FacilityEntity
import com.nstu.technician.data.database.entity.job.JobTypeEntity
import com.nstu.technician.data.database.entity.job.MaintenanceEntity
import com.nstu.technician.data.database.entity.job.MaintenanceJobEntity
import com.nstu.technician.data.database.entity.tool.*
import com.nstu.technician.data.database.entity.user.AccountEntity
import com.nstu.technician.data.database.entity.user.TechnicianEntity
import com.nstu.technician.data.database.entity.user.UserEntity

@Database(
    entities = [UserEntity::class, AccountEntity::class, TechnicianEntity::class, AddressEntity::class,
        ArtifactEntity::class, GPSEntity::class, ContractEntity::class, ContractorEntity::class,
        FacilityEntity::class, JobTypeEntity::class, MaintenanceEntity::class, MaintenanceJobEntity::class,
        ComponentEntity::class, ComponentUnitEntity::class, ComponentTypeEntity::class, ImplementsEntity::class
        ,ImplementUnitEntity::class, ShiftEntity::class],
    version = AppDataBase.VERSION_DATABASE
)
abstract class TestAppDataBase : AppDataBase() {

    companion object {
        const val DATABASE_NAME = "com.nstu.technician.data.database.test"
        const val VERSION_DATABASE = 1
    }
}