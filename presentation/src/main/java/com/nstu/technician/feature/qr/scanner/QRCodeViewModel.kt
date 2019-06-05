package com.nstu.technician.feature.qr.scanner

import androidx.lifecycle.ViewModel
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.maintenance.StartMaintenanceUseCase
import com.nstu.technician.feature.common.LoaderVM

class QRCodeViewModel(
    private val maintenanceId: Long,
    private val startMaintenanceUseCase: StartMaintenanceUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "QRCodeViewModel"
    }

    val loader: LoaderVM = LoaderVM()

    fun startMaintenance(callUseCase: CallUseCase<Unit>) {
        loader.launchDataLoad {
            startMaintenanceUseCase.execute(object : CallUseCase<Unit> {
                override suspend fun onSuccess(result: Unit) {
                    callUseCase.onSuccess(result)
                }

                override suspend fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }

            }, StartMaintenanceUseCase.Companion.Param.findById(maintenanceId))
        }
    }
}