package task.data.features

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import task.data.Properties

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("FeatureCollection")
data class FeatureCollection(val features: List<IFeature>, val properties: Properties?) : IFeature()