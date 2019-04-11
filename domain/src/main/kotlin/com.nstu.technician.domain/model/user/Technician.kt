package com.nstu.technician.domain.model.user

data class Technician(
    val oid: Int,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var user: User
){
}