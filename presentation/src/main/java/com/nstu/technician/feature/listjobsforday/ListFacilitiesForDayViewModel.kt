package com.nstu.technician.feature.listjobsforday

import androidx.lifecycle.ViewModel
import com.nstu.technician.feature.common.LoaderViewModel

class ListFacilitiesForDayViewModel : ViewModel() {

    val loader: LoaderViewModel = LoaderViewModel()

    fun loadFacilities() {
        loader.launchDataLoad {

        }
    }

}