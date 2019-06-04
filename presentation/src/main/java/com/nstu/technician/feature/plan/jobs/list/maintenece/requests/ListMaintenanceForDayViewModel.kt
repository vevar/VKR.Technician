package com.nstu.technician.feature.plan.jobs.list.maintenece.requests

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.nstu.technician.R
import com.nstu.technician.domain.model.facility.maintenance.Maintenance
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.shift.GetListMaintenanceUseCase
import com.nstu.technician.domain.usecase.shift.StartShiftUseCase
import dagger.Lazy
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListMaintenanceForDayViewModel(
    private val idShift: Long,
    val isCurrentDay: Boolean,
    private val getListMaintenanceUseCase: GetListMaintenanceUseCase,
    private val startShiftUseCase: Lazy<StartShiftUseCase>
) : ViewModel() {
    companion object {
        private const val TAG = "Maintenances_ViewModel"
    }

    private val _listMaintenance = MutableLiveData<List<Maintenance>>(listOf())
    val listMaintenance: LiveData<List<Maintenance>>
        get() = _listMaintenance
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isBottomBtnVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    val isBottomBtnVisible: LiveData<Boolean>
        get() = _isBottomBtnVisible
    val btnBottomText: LiveData<Int> = MediatorLiveData<Int>().apply {
        addSource(_isBottomBtnVisible) {
            if (it) {
                if (isCurrentDay) {
                    this.value = R.string.btn_start_shift
                } else {
                    this.value = R.string.btn_go_to_current_day
                }
            }
        }
    }


    fun loadListMaintenance() {
        launchDataLoad {
            getListMaintenanceUseCase.execute(object : CallUseCase<List<Maintenance>> {
                override suspend fun onSuccess(result: List<Maintenance>) {
                    _listMaintenance.value = result
                    _isBottomBtnVisible.value = true
                }

                override suspend fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }, GetListMaintenanceUseCase.Param.forShift(idShift))
        }
    }

    fun startShift() {
        viewModelScope.launch {
            startShiftUseCase.get().execute(object : CallUseCase<Unit> {
                override suspend fun onSuccess(result: Unit) {
                    Log.d(TAG, "startShiftUseCase done")
                    _isBottomBtnVisible.value = false
                }

                override suspend fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }

            }, StartShiftUseCase.Param.forShift(idShift))
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d(TAG, "(${this@ListMaintenanceForDayViewModel})Loader is called")
                block()
            } finally {
                _isLoading.value = false
                Log.d(TAG, "(${this@ListMaintenanceForDayViewModel})finally is called")
            }
            Log.d(TAG, "(${this@ListMaintenanceForDayViewModel})launch is finished")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "$this is cleared")
    }
}