package omok.model.omokGame

import omok.model.board.Position
import omok.model.board.Stone

interface GameEventListener {
    fun onGameStart()

    fun onGameEnd(winner: Stone)

    fun onStonePlaced(
        position: Position,
        currentStone: Stone,
    )
}
