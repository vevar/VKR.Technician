package com.alxminayev.generator.data

import com.google.auto.service.AutoService
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic


@AutoService(Processor::class)
@SupportedOptions(DataGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class DataGenerator : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Model::class.java.name, Ignore::class.java.name)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        val models = roundEnvironment?.getElementsAnnotatedWith(Model::class.java)
        val typeUtils = processingEnv.typeUtils
        models?.forEach { element ->
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, typeUtils.asElement(element.asType()).toString())

            val modelName = element.simpleName
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, modelName)

            val fields: MutableMap<String, Field> = mutableMapOf()

            element.enclosedElements.filter { it.kind.isField && it.simpleName.toString() != "Companion" }
                .forEach { field ->
                    val type = field.asType()
                    val asElement = typeUtils.asElement(type)
                    when {
                        type.kind.isPrimitive -> {
                            processingEnv.messager.printMessage(
                                Diagnostic.Kind.NOTE,
                                "${field.simpleName} ${type.kind.name}"
                            )
                            fields[field.simpleName.toString()] =
                                Field(field.simpleName.toString(), Primitive(type.kind.name))
                        }
                        asElement.toString() == "java.lang.String" -> {
                            processingEnv.messager.printMessage(
                                Diagnostic.Kind.NOTE,
                                "${field.simpleName} $asElement"

                            )
                            fields[field.simpleName.toString()] =
                                Field(field.simpleName.toString(), Primitive(asElement.toString()))
                        }
                        else -> {
                            processingEnv.messager.printMessage(
                                Diagnostic.Kind.NOTE, "${field.simpleName} $asElement"
                            )
                            if (type is DeclaredType) {
                                if (Collection.isCollection(type.toString())) {
                                    type.typeArguments.forEach {
                                        processingEnv.messager.printMessage(
                                            Diagnostic.Kind.NOTE, "$it"
                                        )
                                    }
                                }
                            }
                        }

                    }

                }

        }
        return false
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        println("getSupportedSourceVersion")
        return SourceVersion.latest()
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}