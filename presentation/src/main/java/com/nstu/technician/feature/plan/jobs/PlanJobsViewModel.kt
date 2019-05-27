package com.nstu.technician.feature.plan.jobs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nstu.technician.domain.model.MiniShift
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.shift.GetListShiftsUseCase
import com.nstu.technician.domain.usecase.shift.StartShiftUseCase
import dagger.Lazy
import kotlinx.coroutines.launch
import java.util.*

class PlanJobsViewModel(
    private val technicianId: Long,
    private val getShiftsUseCase: GetListShiftsUseCase,
    private val startShiftUseCase: Lazy<StartShiftUseCase>
) : ViewModel() {
    companion object {
        private const val TAG = "PlanJobsViewModel"
    }

    var indexCurrentPosition: Int? = null

    private val _data: MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data>
        get() = _data

    private val _messageIdResource: MutableLiveData<Int> = MutableLiveData()
    val message: LiveData<Int>
        get() = _messageIdResource
    private val currentDate: Calendar = Calendar.getInstance()


    fun loadPlanJobs() {
        viewModelScope.launch {
            getShiftsUseCase.execute(object : CallUseCase<List<MiniShift>> {
                override suspend fun onSuccess(result: List<MiniShift>) {
                    val indexCurrentShift = findIndexOfCurrentShift(result) ?: 0
                    _data.value = Data(result, indexCurrentShift)
                }

                override suspend fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }

            }, GetListShiftsUseCase.Param.forTechnician(technicianId))
        }
    }

    private fun findIndexOfCurrentShift(list: List<MiniShift>): Int? {
        val currentDayYear = currentDate.get(Calendar.DAY_OF_YEAR)
        val currentYear = currentDate.get(Calendar.YEAR)
        return list.indexOfFirst { shift ->
            val shiftDate = Calendar.getInstance()
            shiftDate.timeInMillis = shift.date.timeInMS
            val dayYear = shiftDate.get(Calendar.DAY_OF_YEAR)
            val year = shiftDate.get(Calendar.YEAR)

            currentYear == year && currentDayYear == dayYear
        }
    }

    fun startShift() {
        Log.d(TAG,"Method startShift is called")
        viewModelScope.launch {
            startShiftUseCase.get().execute(object : CallUseCase<Unit> {
                override suspend fun onSuccess(result: Unit) {
                }

                override suspend fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }

            }, Unit)
        }
    }

    class Data(
        val shifts: List<MiniShift>,
        val indexCurrentDay: Int
    )

}