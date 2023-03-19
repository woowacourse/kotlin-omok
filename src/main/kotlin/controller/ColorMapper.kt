package controller

import domain.Color
import dto.ColorDTO

object ColorMapper : Mapper<Color, ColorDTO> {

    override fun Color.toDTO(): ColorDTO {
        return when (this) {
            Color.BLACK -> ColorDTO.BLACK
            Color.WHITE -> ColorDTO.WHITE
        }
    }

    override fun ColorDTO.toDomain(): Color {
        return when (this) {
            ColorDTO.BLACK -> Color.BLACK
            ColorDTO.WHITE -> Color.WHITE
        }
    }
}
