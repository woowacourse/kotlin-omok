package view

import dto.PointDTO
import dto.StoneDTO

interface RenderBoard {
    fun render(stones: List<StoneDTO>, size: PointDTO): String
}
