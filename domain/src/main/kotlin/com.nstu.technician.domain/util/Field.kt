package com.nstu.technician.domain.util

abstract class Type(
    open val name: String,
    val isPrimitive: Boolean
) {
    companion object {
        private val listPrimitives = arrayListOf("Int", "Long", "Double", "Boolean", "String", "Float")

        fun isPrimitive(type: String): Boolean {
            return listPrimitives.contains(type)
        }
    }
}

data class Primitive(
    override val name: String
) : Type(name, true)

open class Reference(
    override val name: String
) : Type(name, false)

data class Collection(
    override val name: String,
    val generic: Type
) : Reference(name) {
    companion object {
        fun isCollection(type: String): Boolean {
            val collection = Regex("List").find(type)?.value
            return collection != null
        }

        fun getInstance(fullName: String): Collection {
            val startGenericIndex = fullName.indexOf("<") + 1
            val endGenericIndex = fullName.indexOf(">")
            return Collection(fullName, TypeFactory.getType(fullName.substring(startGenericIndex, endGenericIndex)))
        }
    }
}

class TypeFactory {
    companion object {
        fun getType(type: String): Type {
            val correctType = type.replace("?", "")
            if (Type.isPrimitive(correctType)) {
                return Primitive(correctType)
            }
            if (Collection.isCollection(correctType)) {
                return Collection.getInstance(correctType)
            }
            return Reference(correctType)
        }
    }

}

class Field(
    val name: String,
    val type: Type
)


