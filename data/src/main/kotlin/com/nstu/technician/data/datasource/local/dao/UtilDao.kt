package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import kotlinx.coroutines.runBlocking

@Dao
abstract class UtilDao {

    @Transaction
    open fun  transaction( function: suspend () -> Unit) {
        runBlocking {
            function.invoke()
        }
    }
}