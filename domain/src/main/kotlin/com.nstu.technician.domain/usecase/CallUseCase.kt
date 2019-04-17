package com.nstu.technician.domain.usecase

interface CallUseCase<T> {
    suspend fun onSuccess(result: T)
    suspend fun onFailure(throwable: Throwable)
}