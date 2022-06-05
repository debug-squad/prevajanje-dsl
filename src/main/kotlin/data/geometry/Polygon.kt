package task.data.geometry

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("Polygon")
class Polygon(val coordinates: List<List<List<Float>>>) : IGeometryObject()