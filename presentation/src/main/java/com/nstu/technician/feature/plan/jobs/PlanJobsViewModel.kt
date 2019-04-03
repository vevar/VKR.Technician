package com.nstu.technician.feature.plan.jobs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.job.LoadShiftsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class PlanJobsViewModel(
    private val loadShiftsUseCase: LoadShiftsUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "PlanJobsViewModel"
    }

    private val _data: MutableLiveData<Data> = MutableLiveData()
    val data: LiveData<Data>
        get() = _data

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message
    private val currentDate: Calendar = Calendar.getInstance()


    fun loadPlanJobs() {
        viewModelScope.launch {
            loadShiftsUseCase.execute(object : CallUseCase<List<Shift>> {
                override suspend fun onSuccess(result: List<Shift>) {
                    val indexCurrentShift = findIndexOfCurrentShift(result)
                    if (indexCurrentShift != null) {
                        withContext(Dispatchers.Main) {
                            _data.value = Data(result, indexCurrentShift)
                        }
                    } else {
                        throw NullPointerException("Current day not found")
                    }
                }

                override suspend fun onFailure(throwable: Throwable) {
                    Log.d(TAG, throwable.message)
                }

            }, Unit)
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
        val indexCurrentShift: Int
    )

}