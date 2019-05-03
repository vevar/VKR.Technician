package com.nstu.technician.domain.util

import com.github.sarahbuisson.kotlinparser.KotlinLexer
import com.github.sarahbuisson.kotlinparser.KotlinParser
import com.github.sarahbuisson.kotlinparser.KotlinParserBaseListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File


class ModelParser {

    fun parse(file: File): Model {
        var mPackageName: String? = null
        var mName: String? = null
        val mImports: MutableList<String> = mutableListOf()
        //    Map<fieldName, Type>
        val fields: MutableMap<String, Field> = mutableMapOf()
        val primitiveFields: MutableMap<String, Primitive> = mutableMapOf()

        class ModelParserListener : KotlinParserBaseListener() {

            override fun enterKotlinFile(ctx: KotlinParser.KotlinFileContext?) {
                mPackageName = ctx?.packageHeader()?.identifier()?.text
                println("Package: $mPackageName")
                ctx?.importList()?.importHeader()?.map { it?.identifier()?.text }?.forEach { import ->
                    import?.let {
                        println("import: $import")
                        mImports.add(import)
                    }
                }
            }

            override fun enterClassDeclaration(ctx: KotlinParser.ClassDeclarationContext?) {
                mName = ctx?.simpleIdentifier()?.text
                println("Class name: $mName")
            }

            override fun enterPrimaryConstructor(ctx: KotlinParser.PrimaryConstructorContext?) {
                ctx?.classParameters()?.classParameter()?.forEach {
                    it?.apply {
                        val name = simpleIdentifier().text
                        val type = type().text

                        val field = Field(name, TypeFactory.getType(type))
                        fields[field.name] = field
                        println("[$name, $type]")
                    }
                }
            }

            override fun exitKotlinFile(ctx: KotlinParser.KotlinFileContext?) {
                println("EXIT FILE ")
            }
        }

        val KotlinLexer = KotlinLexer(CharStreams.fromStream(file.inputStream()))

        val commonTokenStream = CommonTokenStream(KotlinLexer)
        val kotlinParser = KotlinParser(commonTokenStream)

        val tree = kotlinParser.kotlinFile()
        val walker = ParseTreeWalker()

        val listener = ModelParserListener()
        walker.walk(listener, tree)

        val packageName = mPackageName ?: throw IllegalStateException("packageName must be set")
        val name = mName ?: throw IllegalStateException("name must be set")
        return Model(packageName, name, imports = mImports, fields = fields, primitiveFields = primitiveFields)

    }
}