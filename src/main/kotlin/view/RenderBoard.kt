package view

import dto.StoneDTO
import dto.VectorDTO

interface RenderBoard {
    fun render(stones: Map<Int, StoneDTO>, size: VectorDTO): String
}
