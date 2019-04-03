package com.nstu.technician.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<T> {

    protected abstract suspend fun task(): T

    suspend fun execute(callUseCase: CallUseCase<T>) {
        try {
            var result: T
            withContext(Dispatchers.IO) {
                result = task()
                withContext(Dispatchers.Main) {
                    callUseCase.onSuccess(result)
                }
            }
        } catch (throwable: Throwable) {
            callUseCase.onFailure(throwable)
        }
    }
}