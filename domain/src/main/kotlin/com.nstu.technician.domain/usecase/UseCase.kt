package com.nstu.technician.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<Result, Param> {

    protected abstract suspend fun task(param: Param): Result

    suspend fun execute(callUseCase: CallUseCase<Result>, param: Param) {
        try {
            var result: Result
            withContext(Dispatchers.IO) {
                result = task(param)
                withContext(Dispatchers.Main) {
                    callUseCase.onSuccess(result)
                }
            }
        } catch (throwable: Throwable) {
            callUseCase.onFailure(throwable)
        }
    }
}