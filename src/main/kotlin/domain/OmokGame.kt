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
        onMove: (State, State, Color, Stone) -> Unit,
        onMoveFail: () -> Unit,
        onFinish: (Color) -> Unit
    ) {
        while (true) {
            if (!successBlackTurn(getStone, onMoveFail, onMove)) successBlackTurn(getStone, onMoveFail, onMove)
            if (isVictory(Color.BLACK, onFinish)) break

            if (!successWhiteTurn(getStone, onMoveFail, onMove)) successWhiteTurn(getStone, onMoveFail, onMove)
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

    private fun successBlackTurn(
        getStone: () -> Stone,
        onMoveFail: () -> Unit,
        onMove: (State, State, Color, Stone) -> Unit
    ): Boolean {
        val blackStone = getStone()
        if (!board.canMove(blackStone)) {
            onMoveFail()
            return false
        }
        board.moveBlack(blackStone)
        onMove(board.getState(Color.BLACK), board.getState(Color.WHITE), Color.WHITE, blackStone)
        return true
    }

    private fun successWhiteTurn(
        getStone: () -> Stone,
        onMoveFail: () -> Unit,
        onMove: (State, State, Color, Stone) -> Unit
    ): Boolean {
        val whiteStone = getStone()
        if (!board.canMove(whiteStone)) {
            onMoveFail()
            return false
        }
        board.moveWhite(whiteStone)
        onMove(board.getState(Color.BLACK), board.getState(Color.WHITE), Color.BLACK, whiteStone)
        return true
    }
}
