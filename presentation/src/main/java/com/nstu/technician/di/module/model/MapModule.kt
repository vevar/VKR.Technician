package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.shift.GetFacilityUseCase
import com.nstu.technician.feature.plan.jobs.map.MapViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MapModule(
    private val idFacility: Long,
    private val mapListener: MapViewModel.MapListener
) {

    @Provides
    fun provideMapVMFactory(getFacilityUseCase: GetFacilityUseCase): BaseViewModelFactory<MapViewModel>{
        return BaseViewModelFactory(MapViewModel::class.java){
            MapViewModel(idFacility, getFacilityUseCase, mapListener)
        }
    }
}