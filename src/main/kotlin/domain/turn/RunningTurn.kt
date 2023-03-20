package domain.turn

import domain.board.PlacedBoard
import domain.stone.Position

abstract class RunningTurn(
    board: PlacedBoard
) : BasedTurn(board) {

    override val isFinished: Boolean = false

    override fun addStone(position: Position): Turn {
        val newBoard = board.putStone(position, curColor)
        if (newBoard === board) return this
        return nextBoard(newBoard, position)
    }

    abstract fun nextBoard(newBoard: PlacedBoard, position: Position): Turn
}
