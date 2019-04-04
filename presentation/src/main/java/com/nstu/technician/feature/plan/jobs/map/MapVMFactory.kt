package com.nstu.technician.feature.plan.jobs.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nstu.technician.domain.usecase.job.LoadFacilityUseCase
import java.lang.IllegalStateException
import javax.inject.Inject

class MapVMFactory @Inject constructor(
    private val loadFacilityUseCase: LoadFacilityUseCase
) : ViewModelProvider.Factory {

    private var idFacility: Int? = null
    private var mapListener: MapViewModel.MapListener? = null

    fun init(idFacility: Int, mapListener: MapViewModel.MapListener) {
        this.idFacility = idFacility
        this.mapListener = mapListener
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            if (idFacility != null && mapListener != null) {
                return MapViewModel(idFacility!!, loadFacilityUseCase, mapListener!!) as T
            } else {
                throw IllegalStateException("Method init not called")
            }
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}