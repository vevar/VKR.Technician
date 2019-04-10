package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.facility.maintenance.Maintenance

interface MaintenanceRepository {

    fun findById(id: Int): Maintenance
}