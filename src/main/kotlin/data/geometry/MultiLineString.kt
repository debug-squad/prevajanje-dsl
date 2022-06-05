package task.data.geometry

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("MultiLineString")
class MultiLineString(val coordinates: List<List<List<Float>>>) : IGeometryObject()