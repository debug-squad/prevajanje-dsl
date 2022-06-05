package task.data.geometry

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("LineString")
class LineString(val coordinates: List<List<Float>>) : IGeometryObject()