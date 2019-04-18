package com.nstu.technician.data.datasource.local.dao

import com.nstu.technician.data.database.entity.common.GPSEntity

interface GpsDao {
    fun findById(id: Long): GPSEntity
    fun save(gpsEntity: GPSEntity)
}
