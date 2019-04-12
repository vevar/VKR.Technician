package com.nstu.technician.domain.exceptions

import android.os.Message

class NetworkNotAvailable : Throwable("Network isn't available")
class SessionTokenIncorrect(): Throwable("SessionToken is incorrect")
class UserNotFoundException : Throwable("User not found")
class UserNotTechnician: Throwable("User isn't technician")
class BadRequestException(message: String): Throwable(message)
class NetworkException(message: String): Throwable(message)