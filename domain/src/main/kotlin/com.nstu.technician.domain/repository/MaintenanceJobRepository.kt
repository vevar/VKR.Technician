package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.facility.maintenance.MaintenanceJob

interface MaintenanceJobRepository: CrudRepository<MaintenanceJob, Long> {
}