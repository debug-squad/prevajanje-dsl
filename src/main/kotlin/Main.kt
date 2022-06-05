package task

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import task.data.features.Feature
import task.data.features.FeatureCollection
import task.data.features.IFeature
import task.data.geometry.*
import task.lexer.EvalAutomaton
import task.lexer.Scanner
import task.lexer.TokenType
import java.io.File
import java.io.FileInputStream
import kotlin.system.exitProcess

fun printTokens(scanner: Scanner) {
    while (true) {
        val token = scanner.getToken() ?: break
        print("${TokenType.name(token.value)}(\"${token.lexeme}\") ")
    }
}

fun main(args: Array<String>) {
    val scanner = Scanner(EvalAutomaton, FileInputStream(File(args[0])))
    val parser = Parser(scanner)

    if (parser.parse()) {
        println(Json {
            serializersModule = SerializersModule {
                polymorphic(IFeature::class, Feature::class, Feature.serializer())
                polymorphic(IFeature::class, FeatureCollection::class, FeatureCollection.serializer())

                polymorphic(IGeometryObject::class, GeometryCollection::class, GeometryCollection.serializer())
                polymorphic(IGeometryObject::class, LineString::class, LineString.serializer())
                polymorphic(IGeometryObject::class, MultiLineString::class, MultiLineString.serializer())
                polymorphic(IGeometryObject::class, MultiPoint::class, MultiPoint.serializer())
                polymorphic(IGeometryObject::class, Point::class, Point.serializer())
                polymorphic(IGeometryObject::class, Polygon::class, Polygon.serializer())
            }
        }.encodeToString(parser.currentFeature))
    } else {
        println("rejected")
        printTokens(scanner)
        exitProcess(0)
    }
}