package controller

import domain.Color
import dto.ColorDTO

object ColorMapper {
    fun domainToDTO(color: Color): ColorDTO {
        return when (color) {
            Color.BLACK -> ColorDTO.BLACK
            Color.WHITE -> ColorDTO.WHITE
        }
    }
}
