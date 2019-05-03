package com.nstu.technician.domain.util

data class Model(
    val packageName: String,
    val name: String,
    val imports: List<String> = listOf(),
    val fields: Map<String, Field> = mapOf(),
    val primitiveFields: Map<String, Primitive> = mapOf()

) {

}