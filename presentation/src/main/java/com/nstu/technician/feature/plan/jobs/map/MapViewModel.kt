package com.nstu.technician.feature.plan.jobs.map

import android.location.Location
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {


    var targetLatitude: Double = 0.0
     var targetLongitude: Double = 0.0
    private var targetLocation: Location? = null

    fun init(latitude: Double, longitude: Double) {
    }


}