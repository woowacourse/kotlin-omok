package controller

import domain.Coordinate
import domain.Stone
import dto.StoneDTO

object StoneMapper : Mapper<Stone, StoneDTO> {
    override fun domainToDTO(domain: Stone): StoneDTO {
        return StoneDTO(
            ColorMapper.domainToDTO(domain.color), VectorMapper.domainToDTO(domain.coordinate.vector)
        )
    }

    override fun dtoToDomain(dto: StoneDTO): Stone {
        val point = VectorMapper.dtoToDomain(dto.coordinate)
        return Stone(
            ColorMapper.dtoToDomain(dto.color), Coordinate.from(point.x, point.y).getOrNull()!!
        )
    }
}
