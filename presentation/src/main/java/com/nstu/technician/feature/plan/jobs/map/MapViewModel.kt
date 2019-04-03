package com.nstu.technician.feature.plan.jobs.map

import android.location.Location
import androidx.lifecycle.ViewModel
import com.nstu.technician.domain.model.facility.GPSPoint

class MapViewModel(
    private val mapListener: MapListener
) : ViewModel() {
    var targetGPSPoints: List<GPSPoint> = listOf()
    var mainTargetGPSPoint: GPSPoint = GPSPoint(0.0, 0.0)
    var deviceGPSPoint: GPSPoint = GPSPoint(0.0, 0.0)

    private var targetLocation: Location? = null

    fun init(latitude: Double, longitude: Double) {
        mainTargetGPSPoint = GPSPoint(latitude, longitude)
    }

    fun goToMainTarget() {
        mapListener.onGoToMainTarget(mainTargetGPSPoint)
    }

    interface MapListener {
        fun onGoToMainTarget(gpsPoint: GPSPoint)
    }

}