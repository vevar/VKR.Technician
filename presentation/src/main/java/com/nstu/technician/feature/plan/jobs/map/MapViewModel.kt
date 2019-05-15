package com.nstu.technician.feature.plan.jobs.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MarkerOptions
import com.nstu.technician.domain.model.common.GPSPoint
import com.nstu.technician.domain.model.common.GpsObject
import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.shift.GetFacilityUseCase
import kotlinx.coroutines.launch

class MapViewModel(
    private val idFacility: Long,
    private val getFacilityUseCase: GetFacilityUseCase,
    private val mapListener: MapListener
) : ViewModel() {
    companion object {
        const val TAG = "MapViewModel"
    }

    private val _mainFacility: MutableLiveData<Facility> = MutableLiveData()
    val mainFacility: LiveData<Facility>
        get() = _mainFacility


    fun goToMainTarget() {
        Log.d(TAG, "goToMainTarget is called")
        mapListener.onGoToMainTarget(
            mainFacility.value?.address?.location ?: throw NullPointerException("mainFacility is null")
        )
    }

    fun goToDevice() {
        Log.d(TAG, "goToDevice is called")
    }

    fun loadFacility() {
        viewModelScope.launch {
            getFacilityUseCase.execute(object : CallUseCase<Facility> {
                override suspend fun onSuccess(result: Facility) {
                    Log.d(TAG, "GetFacilityUseCase is executed success")
                    _mainFacility.value = result
                }

                override suspend fun onFailure(throwable: Throwable) {
                    Log.d(TAG, throwable.message)
                }

            }, GetFacilityUseCase.Param.byIdFacility(idFacility))
        }
    }

    interface MapListener {
        fun onGoToMainTarget(gpsObject: GpsObject)
        fun onGoToDevicePosition(gpsPoint: GPSPoint)
        fun onUpdateDevicePosition(marker: MarkerOptions)
    }

}