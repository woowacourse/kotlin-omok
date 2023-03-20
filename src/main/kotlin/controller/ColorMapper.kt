package controller

import domain.Color
import dto.ColorDTO

object ColorMapper : Mapper<Color, ColorDTO> {

    override fun Color.toDTO(): ColorDTO {
        return when (this) {
            Color.Black -> ColorDTO.BLACK
            Color.White -> ColorDTO.WHITE
        }
    }

    override fun ColorDTO.toDomain(): Color {
        return when (this) {
            ColorDTO.BLACK -> Color.Black
            ColorDTO.WHITE -> Color.White
        }
    }
}
