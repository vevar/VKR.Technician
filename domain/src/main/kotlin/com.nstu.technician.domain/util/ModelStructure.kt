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
                model.fields.forEach { entry ->
                    val field = entry.value
                    if (field.isPrimitive && field.isEntity) {
                        modelCollectionMap[field.name]?.also {
                            dependencies.add(it)
                        } ?: throw IllegalStateException("Model not found")
                    }
                }
                mMap[model] = dependencies
            }
            return this
        }

        fun build(): ModelStructure {
            return ModelStructure(mMap)
        }
    }
}