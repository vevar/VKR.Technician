package com.nstu.technician.feature.util

import com.nstu.technician.domain.model.facility.Facility


fun Facility.getQRCode(): String {
    return "$name ($identifier) $address"
}
