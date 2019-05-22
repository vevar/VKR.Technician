package com.nstu.technician.feature.problem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nstu.technician.R
import com.nstu.technician.domain.UndefinedType
import com.nstu.technician.domain.model.Problem
import com.nstu.technician.domain.usecase.CallUseCase
import com.nstu.technician.domain.usecase.maintenance.job.SendProblemUseCase
import kotlinx.coroutines.launch

class ProblemViewModel constructor(
    private val maintenanceJobId: Long,
    private val useCase: SendProblemUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "ProblemViewModel"
    }

    val typesProblems = R.array.types_problems

    var mChooseTypeProblem: Int = UndefinedType
    var mComment: MutableLiveData<String> = MutableLiveData("")

    private val _message: MutableLiveData<Int> = MutableLiveData()
    val mMessage: LiveData<Int>
        get() = _message

    fun send() {
        viewModelScope.launch {
            val problem = createProblem()

            if (problem != null) {
                useCase.execute(
                    object : CallUseCase<Problem> {
                        override suspend fun onSuccess(result: Problem) {
                            Log.d(TAG, "problem saved with id=${result.oid}")
                        }

                        override suspend fun onFailure(throwable: Throwable) {
                            throwable.printStackTrace()
                        }
                    }, SendProblemUseCase.Param.problemForMaintenanceJob(
                        maintenanceJobId = maintenanceJobId,
                        problem = problem
                    )
                )
            }
        }
    }

    private fun createProblem(): Problem? {
        val type = mChooseTypeProblem
        val comment = mComment.value
        if (type == UndefinedType) {
            _message.value = R.string.lbl_need_input_type_problem
            return null
        }
        if (comment == null || comment.isBlank()) {
            _message.value = R.string.lbl_need_input_comment
            return null
        }
        return Problem(0, type, comment)
    }

}
