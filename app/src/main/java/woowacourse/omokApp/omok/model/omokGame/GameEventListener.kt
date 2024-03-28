package woowacourse.omokApp.omok.model.omokGame

import woowacourse.omokApp.omok.model.board.Position
import woowacourse.omokApp.omok.model.board.Stone

interface GameEventListener {
    fun onGameStart()

    fun onGameEnd(winner: Stone)

    fun printBoard(
        board: Array<Array<Stone>>,
        forbiddenPositions: List<Position>,
    )
}
