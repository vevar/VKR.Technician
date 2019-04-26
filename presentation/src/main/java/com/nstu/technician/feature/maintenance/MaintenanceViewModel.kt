package com.nstu.technician.feature.maintenance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nstu.technician.domain.model.document.Contract
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.usecase.job.LoadDetailMaintenanceUseCase
import com.nstu.technician.feature.common.LoaderVM

class MaintenanceViewModel(
    maintenance: Maintenance,
    private val loadDetailMaintenanceUseCase: LoadDetailMaintenanceUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "MaintenanceViewModel"
    }

    private val _maintenance: MutableLiveData<Maintenance> = MutableLiveData()
    val maintenance: LiveData<Maintenance>
        get() = _maintenance

    val loader: LoaderVM = LoaderVM()
    val contract: LiveData<Contract>

    private val _isListJobsVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    val isListJobsVisible: LiveData<Boolean>
        get() = _isListJobsVisible

    init {
        this._maintenance.value = maintenance
        contract = Transformations.map(_maintenance) { it.facility.contract }
    }

    fun loadDetailMaintenance() {

//        loader.launchDataLoad {
//            loadDetailMaintenanceUseCase.execute(object : CallUseCase<Maintenance> {
//                override suspend fun onSuccess(result: Maintenance) {
//                    _maintenance.value = result
//                    _contract.value = result.facility.contract
//                    Log.d(TAG, "LoadDetailMaintenanceUseCase is invoked success")
//                }
//
//                override suspend fun onFailure(throwable: Throwable) {
//                    Log.d(TAG, "LoadDetailMaintenanceUseCase fail")
//                    Log.d(TAG, throwable.message)
//                }
//            }, LoadDetailMaintenanceUseCase.Companion.Param.byId(idMaintenance))
//        }
    }

    fun showListJobs() {
        _isListJobsVisible.value = true
    }

    fun hideListJobs() {
        _isListJobsVisible.value = false
    }
}