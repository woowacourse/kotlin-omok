package controller

import domain.Vector
import dto.VectorDTO

object VectorMapper : Mapper<Vector, VectorDTO> {
    override fun domainToDTO(domain: Vector): VectorDTO {
        return VectorDTO(domain.x, domain.y)
    }

    override fun dtoToDomain(dto: VectorDTO): Vector {
        return Vector(dto.x, dto.y)
    }
}
