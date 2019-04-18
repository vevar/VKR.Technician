package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Transaction

@Dao
interface UtilDao {
    @Transaction
    fun <T> transation(function: () -> T){
        function.invoke()
    }
}