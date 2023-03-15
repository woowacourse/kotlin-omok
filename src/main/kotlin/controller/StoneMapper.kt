package controller

import domain.Stone
import dto.StoneDTO

object StoneMapper {
    fun domainToDTO(stone: Stone): StoneDTO {
        return StoneDTO(
            ColorMapper.domainToDTO(stone.color),
            PointMapper.domainToDTO(stone.coordinate)
        )
    }
}
