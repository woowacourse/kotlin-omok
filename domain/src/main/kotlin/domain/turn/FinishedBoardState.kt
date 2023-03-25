package domain.turn

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class FinishedBoardState(
    board: Map<Position, Color?>,
    latestStone: Stone,
) : BoardState(board, latestStone) {
    init {
        check(board[latestStone.position] != null) { ERROR_NOT_EXIST_LATEST_STONE_IN_BOARD }
    }

    override fun putStone(stone: Stone): BoardState = this

    override fun isFinished(): Boolean = true

    companion object {
        private const val ERROR_NOT_EXIST_LATEST_STONE_IN_BOARD = "[ERROR] 마지막 돌이 보드에 존재하지 않습니다."
    }
}
