package domain.turn

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

abstract class BoardState(
    private val _board: Map<Position, Color?>,
    val latestStone: Stone?
) {
    val board: Map<Position, Color?>
        get() = _board.toMap()

    init {
        latestStone?.let {
            check(_board[it.position] != null) { ERROR_NOT_EXIST_LATEST_STONE }
        }
    }

    abstract fun putStone(stone: Stone): BoardState
    abstract fun isFinished(): Boolean

    companion object {
        private const val ERROR_NOT_EXIST_LATEST_STONE = "[ERROR] 마지막 위치에 놓은 돌이 보드에 존재하지 않습니다."
    }
}
