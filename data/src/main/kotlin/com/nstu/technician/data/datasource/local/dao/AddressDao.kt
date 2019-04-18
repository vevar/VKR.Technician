package com.nstu.technician.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.nstu.technician.data.database.entity.common.AddressEntity

@Dao
interface AddressDao {

    @Query("SELECT * FROM addressentity WHERE oid=:id")
    fun findById(id: Long): AddressEntity?
}