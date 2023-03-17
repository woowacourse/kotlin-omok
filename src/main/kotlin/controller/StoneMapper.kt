package controller

import domain.Coordinate
import domain.Stone
import dto.StoneDTO

object StoneMapper : Mapper<Stone, StoneDTO> {
    override fun domainToDTO(domain: Stone): StoneDTO {
        return StoneDTO(
            ColorMapper.domainToDTO(domain.color), PointMapper.domainToDTO(domain.coordinate.point)
        )
    }

    override fun dtoToDomain(dto: StoneDTO): Stone {
        val point = PointMapper.dtoToDomain(dto.coordinate)
        return Stone(
            ColorMapper.dtoToDomain(dto.color), Coordinate.from(point.x, point.y)!!
        )
    }
}
