package com.nstu.technician.feature.maintenance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nstu.technician.domain.model.facility.Maintenance
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.job.LoadDetailMaintenanceUseCase
import com.nstu.technician.feature.common.LoaderVM

class MaintenanceViewModel(
    private val idMaintenance: Int,
    private val loadDetailMaintenanceUseCase: LoadDetailMaintenanceUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "MaintenanceViewModel"
    }

    private val _maintenance: MutableLiveData<Maintenance> = MutableLiveData()
    val maintenance: LiveData<Maintenance>
        get() = _maintenance

    val loader: LoaderVM = LoaderVM()

    suspend fun loadDetailMaintenance() {
        loader.launchDataLoad {
            loadDetailMaintenanceUseCase.execute(object : CallUseCase<Maintenance> {
                override suspend fun onSuccess(result: Maintenance) {
                    _maintenance.value = result
                    Log.d(TAG, "LoadDetailMaintenanceUseCase is invoked success")
                }

                override suspend fun onFailure(throwable: Throwable) {
                    Log.d(TAG, "LoadDetailMaintenanceUseCase fail")
                    Log.d(TAG, throwable.message)
                }
            }, LoadDetailMaintenanceUseCase.Companion.Param.byId(idMaintenance))
        }
    }
}