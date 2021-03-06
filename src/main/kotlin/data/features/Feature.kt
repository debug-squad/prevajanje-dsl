package task.data.features

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import task.data.Properties
import task.data.geometry.IGeometryObject

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("Feature")
data class Feature(val geometry: IGeometryObject, val properties: Properties?) : IFeature() {
    override fun toDSL(indent: Int): String =
        "${indent(indent)}:${properties?.type ?: "unknown"}${properties?.name?.let { " " + Json.encodeToString(it) } ?: ""} {\n${
            geometry.toDSL(indent + 1).let {
                if (it.isEmpty()) {
                    ""
                } else {
                    it + "\n"
                }
            }
        }${indent(indent)}};"
}