package com.nstu.technician.feature.plan.jobs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.job.LoadShiftsUseCase
import kotlinx.coroutines.launch
import java.util.*

class PlanJobsViewModel(
    private val technicianId: Int,
    private val loadShiftsUseCase: LoadShiftsUseCase
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
            loadShiftsUseCase.execute(object : CallUseCase<List<Shift>> {
                override suspend fun onSuccess(result: List<Shift>) {
                    val indexCurrentShift = findIndexOfCurrentShift(result) ?: 0
                    _data.value = Data(result, indexCurrentShift)
                }

                override suspend fun onFailure(throwable: Throwable) {
                    Log.d(TAG, throwable.message)
                }

            }, LoadShiftsUseCase.Param.forTechnician(technicianId))
        }
    }

    private fun findIndexOfCurrentShift(list: List<Shift>): Int? {
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

    class Data(
        val shifts: List<Shift>,
        val indexCurrentDay: Int
    )

}