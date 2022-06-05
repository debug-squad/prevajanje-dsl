package task.data.geometry

@kotlinx.serialization.Serializable
@kotlinx.serialization.SerialName("GeometryCollection")
class GeometryCollection(val geometries: List<IGeometryObject>) : IGeometryObject()