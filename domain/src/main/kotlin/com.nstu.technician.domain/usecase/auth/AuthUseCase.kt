package com.nstu.technician.domain.usecase.auth

import javax.inject.Inject

class AuthUseCase @Inject constructor() {

    suspend fun auth(username: String, password: String): Any {
        TODO()
    }

    class StudentNotFoundException : Throwable("Student not found")
}