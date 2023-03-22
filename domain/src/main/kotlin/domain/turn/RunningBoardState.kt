package domain.turn

import domain.judgement.Rule
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class RunningBoardState(
    private val rule: Rule,
    board: Map<Position, Color?> = POSITIONS.associateWith { null }.toMap(),
    latestStone: Stone? = null,
) : BoardState(board, latestStone) {

    init {
        latestStone?.let {
            val previousBoard = board.toMutableMap().apply { this[it.position] = null }
            check(rule.isWin(previousBoard, it).not()) { ERROR_ALREADY_FINISHED_BOARD }
        }
    }

    override fun isFinished(): Boolean = false

    private fun isPossiblePut(stone: Stone, rule: Rule): Boolean {
        if (stone.color == Color.BLACK && rule.isForbidden(board, stone)) return false
        return board[stone.position] == null
    }

    override fun putStone(stone: Stone): BoardState {
        if (isPossiblePut(stone, rule).not()) return this
        val nextBoard = board.toMutableMap().apply { this[stone.position] = stone.color }
        return nextBoardState(nextBoard, stone)
    }

    private fun nextBoardState(nextBoard: Map<Position, Color?>, newStone: Stone): BoardState {
        if (rule.isWin(board.toMap(), newStone)) {
            return FinishedBoardState(rule, nextBoard, newStone)
        }
        return RunningBoardState(rule, nextBoard, newStone)
    }

    companion object {
        private val POSITIONS: List<Position> = Position.all()
        private const val ERROR_ALREADY_FINISHED_BOARD = "[ERROR] 이미 오목 게임이 종료된 보드 입니다."
    }
}
