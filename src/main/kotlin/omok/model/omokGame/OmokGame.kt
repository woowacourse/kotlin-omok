package omok.model.omokGame

import omok.model.board.Position
import omok.model.board.Stone

class OmokGame {
    lateinit var currentStone: Stone
    val board = Board()
    var listener: GameEventListener? = null

    fun startGame() {
        currentStone = Stone.BLACK
        listener?.onGameStart()
    }

    fun endGame(winner: Stone) {
        listener?.onGameEnd(winner)
    }

    fun placeStone(
        position: Position,
        currentStone: Stone,
    ) {
        board.setStone(position.row, position.column, currentStone)
        board.checkGameOver(position.row, position.column, currentStone)
        listener?.onStonePlaced(position, currentStone)
    }
}
