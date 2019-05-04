package com.alxminayev.generator.data

class ModelStructure private constructor(
    val map: Map<ModelDescriptor, List<ModelDescriptor>>
) {
    class Builder {
        private val mMap: MutableMap<ModelDescriptor, MutableList<ModelDescriptor>> = mutableMapOf()

        fun addAllModel(list: List<ModelDescriptor>): Builder {
            val modelCollectionMap = mutableMapOf<String, ModelDescriptor>()
            modelCollectionMap.putAll(list.map { Pair(it.name, it) })

            list.forEach { model ->
                val dependencies = mutableListOf<ModelDescriptor>()
                for (entry in model.fields) {
                    val field = entry.value
                    val type = field.type
                    if (type is Collection) {
                        val bottomType = getBottomType(type)
                        if (bottomType is Reference) {
                            modelCollectionMap[bottomType.name]?.also {
                                dependencies.add(it)
                            } ?: throw IllegalStateException("ModelDescriptor(${bottomType.name}) not found")
                            continue
                        }
                    }
                    if (type is Reference) {
                        modelCollectionMap[type.name]?.also {
                            dependencies.add(it)
                        } ?: throw IllegalStateException("ModelDescriptor(${type.name}) not found")
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