package com.nstu.technician.domain.exceptions

class NetworkNotAvailable : Throwable("Network isn't available")
class UserNotFoundException : Throwable("User not found")
class UserNotTechnician : Throwable("User isn't technician")
class NotFoundException(tag: String, what: String) : Throwable("$tag: Object ($what) not found")
class UnauthorizedException(message: String) : Throwable(message)
class BadRequestException(message: String) : Throwable(message)
class NetworkException(message: String) : Throwable(message)
class UnresolvedException(message: String) : Throwable(message)

class DataNotSaveException(tag: String) : Throwable("$tag: data aren't saved")