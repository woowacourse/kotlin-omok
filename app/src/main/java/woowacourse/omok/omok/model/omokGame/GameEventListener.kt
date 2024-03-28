package woowacourse.omok.omok.model.omokGame

import woowacourse.omok.omok.model.board.Position
import woowacourse.omok.omok.model.board.Stone

interface GameEventListener {
    fun onGameStart()

    fun onGameEnd(winner: Stone)

    fun printBoard(
        board: Array<Array<Stone>>,
        forbiddenPositions: List<Position>,
    )
}
