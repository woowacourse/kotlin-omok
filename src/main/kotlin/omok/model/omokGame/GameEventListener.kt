package omok.model.omokGame

import omok.model.board.Position
import omok.model.board.Stone

interface GameEventListener {
    fun onGameStart()

    fun onGameEnd(winner: Stone)

    fun printBoard(
        board: Array<Array<Stone>>,
        forbiddenPositions: List<Position>,
    )
}
