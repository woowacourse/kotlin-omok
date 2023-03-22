package domain.turn

import domain.judgement.Rule
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class FinishedBoardState(
    rule: Rule,
    board: Map<Position, Color?>,
    latestStone: Stone,
) : BoardState(board, latestStone) {

    init {
        val previousBoard = board.toMutableMap().apply { this[latestStone.position] = null }
        check(rule.isWin(previousBoard, latestStone)) { ERROR_NOT_FINISHED_BOARD }
    }

    override fun putStone(stone: Stone): BoardState = this

    override fun isFinished(): Boolean = true

    companion object {
        private const val ERROR_NOT_FINISHED_BOARD = "[ERROR] 아직 게임이 끝나지 않은 보드 입니다."
    }
}
