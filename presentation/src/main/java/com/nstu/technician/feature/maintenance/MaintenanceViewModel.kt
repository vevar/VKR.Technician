package com.nstu.technician.feature.maintenance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.document.Contractor
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.maintenance.GetDetailMaintenanceUseCase
import com.nstu.technician.feature.common.LoaderVM

class MaintenanceViewModel(
    val maintenanceId: Long,
    private val getDetailMaintenanceUseCase: GetDetailMaintenanceUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "MaintenanceViewModel"
    }

    val loader: LoaderVM = LoaderVM()

    private val _maintenance: MutableLiveData<Maintenance> = MutableLiveData()
    val maintenance: LiveData<Maintenance>
        get() = _maintenance

    val contract: LiveData<Contract> = Transformations.map(_maintenance) { it.facility.contract }
    val contractor: LiveData<Contractor> = Transformations.map(_maintenance) { it.facility.contractor }

    private val _isListJobsVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    val isListJobsVisible: LiveData<Boolean>
        get() = _isListJobsVisible


    fun loadDetailMaintenance() {
        loader.launchDataLoad {
            getDetailMaintenanceUseCase.execute(object : CallUseCase<Maintenance> {
                override suspend fun onSuccess(result: Maintenance) {
                    _maintenance.value = result
                }

                override suspend fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }, GetDetailMaintenanceUseCase.Companion.Param.findById(maintenanceId))
        }
    }
}