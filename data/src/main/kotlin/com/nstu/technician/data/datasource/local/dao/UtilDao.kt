package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import kotlinx.coroutines.runBlocking

@Dao
abstract class UtilDao {

    @Transaction
    open fun transactionSave(function: suspend () -> Long): Long {
        return runBlocking {
            function.invoke()
        }
    }

    @Transaction
    open fun transactionSaveAll(function: suspend () -> List<Long>): List<Long> {
        return runBlocking {
            function.invoke()
        }
    }
}