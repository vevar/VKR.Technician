package com.nstu.technician.feature.plan.jobs.map

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nstu.technician.domain.model.facility.GPSPoint

class MapViewModel(
    private val mapListener: MapListener
) : ViewModel() {
    companion object {
        const val TAG = "MapViewModel"
    }

    var mainTargetGPSPoint: MutableLiveData<GPSPoint> = MutableLiveData()

    private var _deviceMarker = MarkerOptions()
    var deviceGPSPoint: MutableLiveData<GPSPoint> = MutableLiveData(GPSPoint(0.0, 0.0))
    var deviceMarker = Transformations.map(deviceGPSPoint) { gpsPoint ->
        _deviceMarker.position(LatLng(gpsPoint.geoy, gpsPoint.geox))
    }

    fun init(latitude: Double, longitude: Double) {
        mainTargetGPSPoint.value = GPSPoint(latitude, longitude)
    }

    fun goToMainTarget() {
        Log.d(TAG, "goToMainTarget is called")
        mapListener.onGoToMainTarget(
            mainTargetGPSPoint.value ?: throw NullPointerException("mainTargetGPSPoint is null")
        )
    }

    fun goToDevice() {
        Log.d(TAG, "goToDevice is called")
        mapListener.onGoToDevicePosition(deviceGPSPoint.value ?: throw NullPointerException())
    }

    fun updateDevicePosition() {
        Log.d(TAG, "updateDevicePosition is called")
        mapListener.onUpdateDevicePosition(deviceMarker.value ?: throw NullPointerException())
    }

    interface MapListener {
        fun onGoToMainTarget(gpsPoint: GPSPoint)
        fun onGoToDevicePosition(gpsPoint: GPSPoint)
        fun onUpdateDevicePosition(marker: MarkerOptions)
    }

}