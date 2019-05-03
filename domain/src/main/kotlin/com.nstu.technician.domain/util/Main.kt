package com.nstu.technician.domain.util

import java.io.File

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val modelParser = ModelParser()
        val file =
            File("C:\\Projects\\Mobile\\VKR.Technician\\domain\\src\\main\\kotlin\\com.nstu.technician.domain\\model")
        val models = mutableListOf<Model>()
        file.walkTopDown().forEach {
            if (it.isFile) {
                models.add(modelParser.parse(it))
            }
        }

        val modelStructure = ModelStructure.Builder()
            .addAllModel(models)
            .build()

        println()
    }
}