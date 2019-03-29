package com.nstu.technician.feature.plan.jobs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxminyaev.ratingnstustudent.domain.usecase.auth.AuthUseCase
import com.nstu.technician.feature.util.TAG_PRESENTATION
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlanJobsViewModel : ViewModel() {

    private val _days: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val days: LiveData<MutableList<String>>
        get() = _days
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _listMaintenance = MutableLiveData<List<Any>>()
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message

    private fun initWeek() {
        val list = mutableListOf<String>()

        for (i in 1..14) {
            list.add("$i января")
        }
        _days.value = list
    }

    fun loadPlanJobs() {
        launchDataLoad {
            initWeek()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (exception: AuthUseCase.StudentNotFoundException) {
                Log.d(TAG_PRESENTATION, exception.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}