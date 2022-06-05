package task.data.geometry

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("MultiPoint")
class MultiPoint(val coordinates: List<List<Float>>) : IGeometryObject()
