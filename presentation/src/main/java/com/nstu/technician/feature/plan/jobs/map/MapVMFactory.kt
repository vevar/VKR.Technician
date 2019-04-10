package com.nstu.technician.feature.plan.jobs.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nstu.technician.domain.usecase.job.LoadFacilityUseCase
import com.nstu.technician.feature.util.BaseViewModelFactory
import java.lang.IllegalStateException
import javax.inject.Inject

class MapVMFactory @Inject constructor(
    private val loadFacilityUseCase: LoadFacilityUseCase
) : BaseViewModelFactory<MapViewModel>(MapViewModel::class.java) {

    private var idFacility: Int? = null
    private var mapListener: MapViewModel.MapListener? = null

    fun init(idFacility: Int, mapListener: MapViewModel.MapListener) {
        this.idFacility = idFacility
        this.mapListener = mapListener
    }

    override fun createViewModel(): MapViewModel {
        return MapViewModel(idFacility!!, loadFacilityUseCase, mapListener!!)
    }
}