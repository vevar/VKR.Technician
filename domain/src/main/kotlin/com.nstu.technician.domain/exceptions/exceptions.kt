package com.nstu.technician.domain.exceptions

class NetworkNotAvailable : Throwable("Network isn't available")
class SessionTokenIncorrect(): Throwable("SessionToken is incorrect")
class UserNotFound : Throwable("User not found")
class UserNotTechnician: Throwable("User isn't technician")
