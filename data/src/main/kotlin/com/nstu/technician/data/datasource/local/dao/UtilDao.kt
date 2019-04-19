package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Transaction

@Dao
abstract class UtilDao {

    @Transaction
    open fun  transaction(function: () -> Unit) {
        function.invoke()
    }
}