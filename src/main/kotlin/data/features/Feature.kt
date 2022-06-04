package task.data.features

import task.data.Properties
import task.data.geometry.IGeometryObject

data class Feature(val geometry: IGeometryObject, val properties: Properties?) : IFeature()