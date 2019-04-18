package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.ShiftDataSource
import com.nstu.technician.data.datasource.local.dao.*
import com.nstu.technician.data.dto.job.ShiftDTO
import com.nstu.technician.data.until.*
import javax.inject.Inject

class ShiftLocalSource @Inject constructor(
    private val shiftDao: ShiftDao,
    private val maintenanceDao: MaintenanceDao,
    private val facilityDao: FacilityDao,
    private val addressDao: AddressDao,
    private val gpsDao: GpsDao
) : ShiftDataSource {
    override suspend fun findByTechnicianIdAndTimePeriod(
        technicianId: Long,
        startTime: Long,
        endTime: Long
    ): List<ShiftDTO>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findById(id: Long): ShiftDTO? {
        return shiftDao.findById(id)?.let { shiftEntity ->
            maintenanceDao.findByIdShift(shiftEntity.oid)?.let { listMaintenanceEntities ->
                shiftEntity.convertToShiftDTO(listMaintenanceEntities.let {
                    it.map { maintenanceEntity ->
                        val facilityDTO = facilityDao.findById(maintenanceEntity.facilityId)?.let { facilityEntity ->
                            val addressDTO = addressDao.findById(facilityEntity.addressId)?.let { addressEntity ->
                                gpsDao.findById(addressEntity.gpsPointId)?.convertToGpsPointDTO()?.let { GPSPointDTO ->
                                    addressEntity.convertToAddressDTO(GPSPointDTO)
                                }
                            } ?: throw NullPointerException("address not found")
                            facilityEntity.convertToFacilityDTO(addressDTO)
                        } ?: throw NullPointerException("facility not found")
                        maintenanceEntity.convertToMaintenanceDTO(facilityDTO)
                    }
                })
            }
        }

    }

    override suspend fun save(shiftDTO: ShiftDTO) {

        shiftDao.save(shiftDTO.convertToShiftEntity())
        shiftDTO.visits?.let { listMaintenance ->
            listMaintenance.map { link ->
                link.convertToObject { maintenanceDTO ->
                    maintenanceDTO.convertToMaintenanceEntity(shiftDTO.oid)
                }
            }.also { listMaintenanceEntities ->
                maintenanceDao.saveAll(listMaintenanceEntities)
            }
        }
    }
}