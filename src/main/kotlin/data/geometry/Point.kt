package task.data.geometry

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("Point")
class Point(val coordinates: List<Float>) : IGeometryObject()