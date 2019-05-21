package com.nstu.technician.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

abstract class UseCase<Result, Param> {

    protected val executor = Dispatchers.IO
    protected val postExecutor = Dispatchers.Main

    protected abstract suspend fun task(param: Param): Result

    suspend fun execute(callUseCase: CallUseCase<Result>, param: Param) {
        try {
            var result: Result
            withContext(executor) {
                result = task(param)
                withContext(postExecutor) {
                    callUseCase.onSuccess(result)
                }
            }
        } catch (throwable: Throwable) {
            callUseCase.onFailure(throwable)
        }
    }
}