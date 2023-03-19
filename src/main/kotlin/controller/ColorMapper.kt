package controller

import domain.Color
import dto.ColorDTO

object ColorMapper : Mapper<Color,ColorDTO> {
    override fun domainToDTO(color: Color): ColorDTO {
        return when (color) {
            Color.BLACK -> ColorDTO.BLACK
            Color.WHITE -> ColorDTO.WHITE
        }
    }

    override fun dtoToDomain(colorDTO: ColorDTO): Color {
        return when (colorDTO) {
            ColorDTO.BLACK -> Color.BLACK
            ColorDTO.WHITE -> Color.WHITE
        }
    }
}
