package woowacourse.omok.model.omokGame

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

interface GameEventListener {
    fun onGameStart()

    fun onGameEnd(winner: Stone)

    fun printBoard(
        board: Array<Array<Stone>>,
        forbiddenPositions: List<Position>,
    )
}
