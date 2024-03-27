package omok.model.omokGame

import GameRuleAdapter
import omok.model.board.Position
import omok.model.board.Stone

class OmokGame {
    lateinit var currentStone: Stone
    var board = Board()
    val renjuGameRule =
        GameRuleAdapter().apply {
            setupBoard(board)
        }
    var listener: GameEventListener? = null

    fun startGame(): Stone {
        currentStone = Stone.BLACK
        listener?.onGameStart()
        return currentStone
    }

    fun endGame(winner: Stone) {
        listener?.onGameEnd(winner)
    }

    fun placeStone(
        position: Position,
        currentStone: Stone,
    ) {
        renjuGameRule.placeForRuleCheck(position.row, position.column, currentStone)
        board.setStone(position.row, position.column, currentStone)
        if (renjuGameRule.isWinningMove(position.row, position.column, currentStone)) board.gameOver()
        listener?.onStonePlaced(position, currentStone)
    }
}
