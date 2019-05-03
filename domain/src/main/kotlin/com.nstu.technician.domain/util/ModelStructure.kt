package com.nstu.technician.domain.util

class ModelStructure private constructor(
    val map: Map<Model, List<Model>>
) {
    class Builder {
        private val mMap: MutableMap<Model, MutableList<Model>> = mutableMapOf()

        fun addAllModel(list: List<Model>): Builder {
            val modelCollectionMap = mutableMapOf<String, Model>()
            modelCollectionMap.putAll(list.map { Pair(it.name, it) })

            list.forEach { model ->
                val dependencies = mutableListOf<Model>()
                for (entry in model.fields) {
                    val field = entry.value
                    val type = field.type
                    if (type is Collection) {
                        val bottomType = getBottomType(type)
                        if (bottomType is Reference) {
                            modelCollectionMap[bottomType.name]?.also {
                                dependencies.add(it)
                            } ?: throw IllegalStateException("Model(${bottomType.name}) not found")
                            continue
                        }
                    }
                    if (type is Reference) {
                        modelCollectionMap[type.name]?.also {
                            dependencies.add(it)
                        } ?: throw IllegalStateException("Model(${type.name}) not found")
                        continue
                    }
                }
                mMap[model] = dependencies
            }
            return this
        }

        private fun getBottomType(collection: Collection): Type {
            if (collection.generic is Collection) {
                getBottomType(collection.generic)
            }
            return collection.generic
        }

        fun build(): ModelStructure {
            return ModelStructure(mMap)
        }
    }
}