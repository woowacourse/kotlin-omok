package domain.turn

import domain.judgement.Rule
import domain.stone.Color
import domain.stone.Stone

class RunningBoardState(
    private val rule: Rule,
    board: Board,
    latestStone: Stone,
) : BoardState(board, latestStone) {

    init {
        val previousBoard = board.map.toMutableMap().apply { this[latestStone.position] = null }
        check(rule.isWin(previousBoard, latestStone).not()) { ERROR_ALREADY_FINISHED_BOARD }
    }

    override fun isFinished(): Boolean = false

    private fun isPossiblePut(stone: Stone, rule: Rule): Boolean {
        if (stone.color == Color.BLACK && rule.isForbidden(board.map, stone)) return false
        return board.isAlreadyPut(stone.position).not()
    }

    override fun putStone(stone: Stone): BoardState {
        if (isPossiblePut(stone, rule).not()) return this
        val nextBoard = board.putStone(stone)
        return nextBoardState(nextBoard, stone)
    }

    private fun nextBoardState(nextBoard: Board, newStone: Stone): BoardState {
        if (rule.isWin(board.map, newStone)) {
            return FinishedBoardState(nextBoard, newStone)
        }
        return RunningBoardState(rule, nextBoard, newStone)
    }

    companion object {
        private const val ERROR_ALREADY_FINISHED_BOARD = "[ERROR] 이미 오목 게임이 종료된 보드 입니다."
    }
}
