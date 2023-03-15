package domain

import domain.turn.BlackTurn
import domain.turn.WhiteTurn

class OmokGame(
    private val board: Board = Board(BlackTurn(), WhiteTurn()),
    private val referee: Referee = Referee()
) {
    fun runGame(
        getStone: () -> Stone,
        onFinish: (Color) -> Unit
    ) {
        while (true) {
            val blackStone = getStone()
            blackTurn(blackStone)
            if (isVictory(Color.BLACK, onFinish)) break

            val whiteStone = getStone()
            whiteTurn(whiteStone)
            if (isVictory(Color.WHITE, onFinish)) break
        }
    }

    private fun isVictory(color: Color, onFinish: (Color) -> Unit): Boolean {
        if (referee.isWin(board.getState(color))) {
            onFinish(color)
            return true
        }
        return false
    }

    private fun blackTurn(stone: Stone) {
        if (board.canMove(stone)) {
            board.moveBlack(stone)
        }
    }

    private fun whiteTurn(stone: Stone) {
        if (board.canMove(stone)) {
            board.moveWhite(stone)
        }
    }
}
