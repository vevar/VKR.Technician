package com.nstu.technician.data.database

import androidx.room.RoomDatabase
import com.nstu.technician.data.datasource.local.dao.TechnicianDao
import com.nstu.technician.data.datasource.local.dao.UserDao

abstract class AppDataBase: RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "com.nstu.technician.data.database"
        const val VERSION_DATABASE = 1
    }

    abstract fun getUserDao(): UserDao

    abstract fun getTechnicianDao(): TechnicianDao
}