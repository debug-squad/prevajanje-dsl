package task.data.features

import task.data.Properties

data class FeatureCollection(val features: List<IFeature>, val properties: Properties?) : IFeature()