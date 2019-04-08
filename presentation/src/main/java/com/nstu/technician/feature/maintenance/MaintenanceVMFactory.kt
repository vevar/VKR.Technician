package com.nstu.technician.feature.maintenance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nstu.technician.domain.usecase.job.LoadDetailMaintenanceUseCase
import java.lang.IllegalStateException
import javax.inject.Inject

class
MaintenanceVMFactory @Inject constructor(
    private val loadDetailMaintenanceUseCase: LoadDetailMaintenanceUseCase
) : ViewModelProvider.Factory {

    private var maintenanceId: Int? = null

    fun init(maintenanceId: Int) {
        this.maintenanceId = maintenanceId
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MaintenanceViewModel::class.java)) {
            if (maintenanceId != null) {
                return MaintenanceViewModel(maintenanceId!!, loadDetailMaintenanceUseCase) as T
            } else {
                throw IllegalStateException("Method init() must called")
            }
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}