package domain

import domain.turn.BlackTurn
import domain.turn.State
import domain.turn.WhiteTurn

class OmokGame(
    private val board: Board = Board(BlackTurn(), WhiteTurn()),
    private val referee: Referee = Referee()
) {
    fun runGame(
        getStone: () -> Stone,
        onMove: (State, State) -> Unit,
        onFinish: (Color) -> Unit
    ) {
        while (true) {
            val blackStone = getStone()
            blackTurn(blackStone, onMove)
            if (isVictory(Color.BLACK, onFinish)) break

            val whiteStone = getStone()
            whiteTurn(whiteStone, onMove)
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

    private fun blackTurn(stone: Stone, onMove: (State, State) -> Unit) {
        if (board.canMove(stone)) {
            board.moveBlack(stone)
            onMove(board.getState(Color.BLACK), board.getState(Color.WHITE))
        }
    }

    private fun whiteTurn(stone: Stone, onMove: (State, State) -> Unit) {
        if (board.canMove(stone)) {
            board.moveWhite(stone)
            onMove(board.getState(Color.BLACK), board.getState(Color.WHITE))
        }
    }
}
