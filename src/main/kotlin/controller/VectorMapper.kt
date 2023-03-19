package controller

import domain.Vector
import dto.VectorDTO

object VectorMapper : Mapper<Vector, VectorDTO> {
    override fun Vector.toDTO(): VectorDTO {
        return VectorDTO(x, y)
    }

    override fun VectorDTO.toDomain(): Vector {
        return Vector(x, y)
    }
}
