package task.data.features

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import task.data.Properties
import task.data.geometry.IGeometryObject

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("Feature")
data class Feature(val geometry: IGeometryObject, val properties: Properties?) : IFeature()