package com.nstu.technician.domain.model.user

data class User(
    val oid: Long,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    var sessionToken: String
){
    var account: Account? = null
}