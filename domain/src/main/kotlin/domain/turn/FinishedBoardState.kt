package domain.turn

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class FinishedBoardState(
    board: Map<Position, Color?>,
    latestStone: Stone,
) : BoardState(board, latestStone) {
    override fun putStone(stone: Stone): BoardState = this

    override fun isFinished(): Boolean = true
}
