package com.nstu.technician.data.dto.common

import com.nstu.technician.data.dto.EntityDTO

data class GPSPointDTO(
    override val oid: Long,
    val geoy: Double, //latitude
    val geox: Double, //longitude
    val exact: Boolean = true,
    val state: Int
): EntityDTO(oid)