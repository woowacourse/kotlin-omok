package domain.turn

import domain.stone.Stone

class FinishedBoardState(
    board: Board,
    latestStone: Stone,
) : BoardState(board, latestStone) {
    override fun putStone(stone: Stone): BoardState = this

    override fun isFinished(): Boolean = true
}
