package view

import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import error.OmokError

interface GameView {
    val renderBoard: RenderBoard
    fun startGame()
    fun renderBoard(stones: List<StoneDTO>, size: VectorDTO)
    fun readStone(color: ColorDTO, lastStone: VectorDTO?): OmokError

    fun renderWinner(color: ColorDTO)
    fun renderError(error: String)
}
