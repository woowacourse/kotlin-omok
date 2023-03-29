package controller

import domain.Coordinate
import domain.Stone
import dto.StoneDTO

object StoneMapper : Mapper<Stone, StoneDTO> {
    override fun domainToDTO(source: Stone): StoneDTO {
        return StoneDTO(
            ColorMapper.domainToDTO(source.color),
            PointMapper.domainToDTO(source.coordinate.point)
        )
    }

    override fun dtoToDomain(source: StoneDTO): Stone {
        return Stone(
            ColorMapper.dtoToDomain(source.color),
            Coordinate.from(source.coordinate.x, source.coordinate.y)
        )
    }
}
