package com.nstu.technician.feature.plan.jobs.map

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nstu.technician.domain.model.facility.GPSPoint

class MapViewModel(
    private val mapListener: MapListener
) : ViewModel() {
    var targetGPSPoints: List<GPSPoint> = listOf()
    var mainTargetGPSPoint: GPSPoint = GPSPoint(0.0, 0.0)

    private var _deviceMarker = MarkerOptions()
    var deviceGPSPoint: MutableLiveData<GPSPoint> = MutableLiveData(GPSPoint(0.0, 0.0))
    var deviceMarker = Transformations.map(deviceGPSPoint) { gpsPoint ->
        _deviceMarker.position(LatLng(gpsPoint.geoy, gpsPoint.geox))
    }

    private var targetLocation: Location? = null

    fun init(latitude: Double, longitude: Double) {
        mainTargetGPSPoint = GPSPoint(latitude, longitude)
    }

    fun goToMainTarget() {
        mapListener.onGoToMainTarget(mainTargetGPSPoint)
    }

    fun goToDevice() {
        mapListener.onGoToDevicePosition(deviceGPSPoint.value ?: throw NullPointerException())
    }

    fun updateDevicePosition() {
        mapListener.onUpdateDevicePosition(deviceMarker.value ?: throw NullPointerException())
    }

    interface MapListener {
        fun onGoToMainTarget(gpsPoint: GPSPoint)
        fun onGoToDevicePosition(gpsPoint: GPSPoint)
        fun onUpdateDevicePosition(marker: MarkerOptions)
    }

}