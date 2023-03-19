package domain.board

import domain.stone.Color
import domain.stone.Position

abstract class BasedTurn(protected val board: PlacedBoard) : Turn {
    override fun getBoard(): Map<Position, Color?> = board.getBoards()
}
