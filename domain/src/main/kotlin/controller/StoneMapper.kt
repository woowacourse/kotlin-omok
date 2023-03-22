package controller

import controller.ColorMapper.toDTO
import controller.ColorMapper.toDomain
import controller.VectorMapper.toDTO
import controller.VectorMapper.toDomain
import domain.Coordinate
import domain.Stone
import dto.StoneDTO

object StoneMapper : Mapper<Stone, StoneDTO> {
    override fun Stone.toDTO(): StoneDTO {
        return StoneDTO(
            color.toDTO(), coordinate.vector.toDTO()
        )
    }

    override fun StoneDTO.toDomain(): Stone {
        val point = coordinate.toDomain()
        return Stone(
            color.toDomain(), Coordinate.from(point.x, point.y)!!
        )
    }
}
