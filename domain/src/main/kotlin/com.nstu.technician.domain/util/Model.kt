package com.nstu.technician.domain.util

data class Model(
    val packageName: String,
    val name: String,
    val imports: List<String> = listOf(),
    val fields: Map<String, Field> = mapOf(),
    val primitiveFields: Map<String, PrimitiveType> = mapOf()

) {
    enum class PrimitiveType {
        INT,
        LONG,
        DOUBLE,
        BOOLEAN,
        STRING,
        FLOAT
    }

    class Field private constructor(
        val name: String,
        val type: String,
        val isPrimitive: Boolean,
        val isEntity: Boolean
    ) {
        companion object {
            private val listPrimitives = arrayListOf("Int", "Long", "Double", "Boolean", "String", "Float")

            fun getInstance(name: String, type: String, isEntity: Boolean): Field {
                val isPrimitive = listPrimitives.contains(type)
                return Field(name, type, isPrimitive, isEntity)
            }
        }


    }
}