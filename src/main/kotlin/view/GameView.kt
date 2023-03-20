package view

import dto.ColorDTO
import dto.PointDTO
import dto.StoneDTO

interface GameView {
    val renderBoard: RenderBoard
    fun startGame()
    fun renderBoard(stones: List<StoneDTO>, size: PointDTO)
    fun readStone(color: ColorDTO, lastStone: PointDTO?): PointDTO?

    fun renderWinner(color: ColorDTO)
}
