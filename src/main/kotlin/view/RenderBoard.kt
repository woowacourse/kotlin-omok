package view

import dto.StoneDTO
import dto.VectorDTO

interface RenderBoard {
    fun render(stones: List<StoneDTO>, size: VectorDTO): String
}
