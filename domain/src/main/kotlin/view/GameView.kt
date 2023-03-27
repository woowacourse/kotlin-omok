package view

import dto.VectorSystem
import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO

interface GameView {
    val renderBoard: RenderBoard
    val placeStoneObserver: PlaceStoneObserver
    fun renderStart(color: ColorDTO)
    fun setUpInput()
    fun renderBoard(stones: Map<Int, StoneDTO>, size: VectorDTO)
    fun notifyPlaceStone(placedCoordinate: VectorSystem): Boolean
    fun renderTurn(color: ColorDTO, lastStone: VectorDTO? = null)
    fun renderWinner(color: ColorDTO)
    fun renderError(error: String)
}
