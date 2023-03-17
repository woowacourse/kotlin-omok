package controller

import domain.Color
import dto.ColorDTO

object ColorMapper : Mapper<Color, ColorDTO> {

    override fun domainToDTO(domain: Color): ColorDTO {
        return when (domain) {
            Color.BLACK -> ColorDTO.BLACK
            Color.WHITE -> ColorDTO.WHITE
        }
    }

    override fun dtoToDomain(dto: ColorDTO): Color {
        return when (dto) {
            ColorDTO.BLACK -> Color.BLACK
            ColorDTO.WHITE -> Color.WHITE
        }
    }
}
