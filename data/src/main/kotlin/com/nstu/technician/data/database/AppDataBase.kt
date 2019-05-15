package com.nstu.technician.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nstu.technician.data.database.entity.ProblemEntity
import com.nstu.technician.data.database.entity.ShiftEntity
import com.nstu.technician.data.database.entity.common.AddressEntity
import com.nstu.technician.data.database.entity.common.ArtifactEntity
import com.nstu.technician.data.database.entity.common.GPSEntity
import com.nstu.technician.data.database.entity.document.ContractEntity
import com.nstu.technician.data.database.entity.document.ContractorEntity
import com.nstu.technician.data.database.entity.job.*
import com.nstu.technician.data.database.entity.tool.*
import com.nstu.technician.data.database.entity.user.AccountEntity
import com.nstu.technician.data.database.entity.user.TechnicianEntity
import com.nstu.technician.data.database.entity.user.UserEntity
import com.nstu.technician.data.datasource.local.dao.*

@Database(
    entities = [UserEntity::class, AccountEntity::class, TechnicianEntity::class, AddressEntity::class,
        ArtifactEntity::class, GPSEntity::class, ContractEntity::class, ContractorEntity::class,
        FacilityEntity::class, JobTypeEntity::class, MaintenanceEntity::class, MaintenanceJobEntity::class,
        ComponentEntity::class, ComponentUnitEntity::class, ComponentTypeEntity::class, ImplementsEntity::class
        , ImplementUnitEntity::class, ShiftEntity::class, GPSPointFromShiftEntity::class, ProblemEntity::class,
        JobTypeImplementsJoin::class, MaintenanceJobImplementJoin::class],
    version = AppDataBase.VERSION_DATABASE
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "com.alxminyaev.firefighter.technician"
        const val VERSION_DATABASE = 1
    }

    abstract fun getUserDao(): UserDao
    abstract fun getTechnicianDao(): TechnicianDao
    abstract fun getAccountDao(): AccountDao
    abstract fun getMaintenanceDao(): MaintenanceDao
    abstract fun getFacilityDao(): FacilityDao
    abstract fun getShiftDao(): ShiftDao
    abstract fun getAddressDao(): AddressDao
    abstract fun getGPSDao(): GpsDao
    abstract fun getUtilDao(): UtilDao
    abstract fun getArtifactDao(): ArtifactDao
    abstract fun getContractDao(): ContractDao
    abstract fun getContractorDao(): ContractorDao
    abstract fun getGPSPointFromShiftDao(): GPSPointFromShiftDao
    abstract fun getImplementsDao(): ImplementDao
    abstract fun getImplementUnitDao(): ImplementUnitDao
    abstract fun getComponentDao(): ComponentDao
    abstract fun getComponentTypeDao(): ComponentTypeDao
    abstract fun getComponentUnitDao(): ComponentUnitDao
    abstract fun getJobTypeDao(): JobTypeDao
    abstract fun getMaintenanceJobDao(): MaintenanceJobDao
    abstract fun getProblemDao(): ProblemDao
}