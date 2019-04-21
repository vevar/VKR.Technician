package com.nstu.technician.data.util

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.nstu.technician.data.TestAppDataBase
import com.nstu.technician.data.database.AppDataBase

class DataBaseProvider {

    private val context: Context = ApplicationProvider.getApplicationContext()

    fun getAppDataBase(): AppDataBase {
        return Room.inMemoryDatabaseBuilder(context, TestAppDataBase::class.java).build()
    }
}