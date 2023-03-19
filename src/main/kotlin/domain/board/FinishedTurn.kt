package domain.board

import domain.stone.Position

abstract class FinishedTurn(
    board: PlacedBoard
) : BasedTurn(board) {
    override val isFinished: Boolean = true

    override fun addStone(position: Position): Turn = this
}
