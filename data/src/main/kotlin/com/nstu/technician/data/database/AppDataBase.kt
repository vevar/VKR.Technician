package com.nstu.technician.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nstu.technician.data.database.entity.job.MaintenanceEntity
import com.nstu.technician.data.datasource.local.dao.*
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.TechnicianDTO
import com.nstu.technician.data.dto.user.UserDTO

@Database(
    entities = [UserDTO::class, AccountDTO::class, TechnicianDTO::class, MaintenanceEntity::class],
    version = AppDataBase.VERSION_DATABASE
)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "com.nstu.technician.data.database"
        const val VERSION_DATABASE = 1
    }

    abstract fun getUserDao(): UserDao
    abstract fun getTechnicianDao(): TechnicianDao
    abstract fun getAccountDao(): AccountDao
    abstract fun getMaintenanceDao(): MaintenanceDao
    abstract fun getFacilityDao(): FacilityDao
}