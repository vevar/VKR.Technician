package com.nstu.technician.di.module.model

import com.nstu.technician.domain.usecase.job.LoadFacilityUseCase
import com.nstu.technician.feature.plan.jobs.map.MapViewModel
import com.nstu.technician.feature.util.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MapModule(
    private val idFacility: Int,
    private val mapListener: MapViewModel.MapListener
) {

    @Provides
    fun provideMapVMFactory(loadFacilityUseCase: LoadFacilityUseCase): BaseViewModelFactory<MapViewModel>{
        return BaseViewModelFactory(MapViewModel::class.java){
            MapViewModel(idFacility, loadFacilityUseCase, mapListener)
        }
    }
}