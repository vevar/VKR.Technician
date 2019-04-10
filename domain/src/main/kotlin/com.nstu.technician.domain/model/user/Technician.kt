package com.nstu.technician.domain.model.user

data class Technician(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String
) {
    var token: String? = null
    var account: Account? = null
}