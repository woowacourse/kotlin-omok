package domain.turn

import domain.stone.Stone

abstract class BoardState(
    val board: Board,
    val latestStone: Stone?
) {

    init {
        latestStone?.let {
            check(board.isAlreadyPut(it.position)) { ERROR_NOT_EXIST_LATEST_STONE }
        }
    }

    abstract fun putStone(stone: Stone): BoardState
    abstract fun isFinished(): Boolean

    companion object {
        private const val ERROR_NOT_EXIST_LATEST_STONE = "[ERROR] 마지막 위치에 놓은 돌이 보드에 존재하지 않습니다."
    }
}
