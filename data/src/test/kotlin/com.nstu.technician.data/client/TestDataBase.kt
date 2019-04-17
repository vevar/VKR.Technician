package com.nstu.technician.data.client

import com.nstu.technician.data.database.AppDataBase

abstract class TestDataBase: AppDataBase(){

    companion object {
        const val DATABASE_NAME = "test.com.nstu.technician.data.database"
        const val VERSION_DATABASE = 1
    }

}