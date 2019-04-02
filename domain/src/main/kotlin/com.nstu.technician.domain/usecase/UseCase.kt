package com.nstu.technician.domain.usecase

abstract class UseCase<T> {

    protected abstract suspend fun task(): T

    suspend fun execute(callUseCase: CallUseCase<T>) {
        try {
            callUseCase.onSuccess(task())
        } catch (throwable: Throwable) {
            callUseCase.onFailure(throwable)
        }
    }
}