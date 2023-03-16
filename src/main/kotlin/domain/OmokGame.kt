package domain

import domain.board.Board
import domain.board.OmokBoard

class OmokGame(
    private val omokBoard: OmokBoard = OmokBoard(),
    private val referee: Referee = Referee()
) {
    fun runGame(
        getStone: () -> Stone,
        onMove: (Board, State, Stone) -> Unit,
        onMoveFail: () -> Unit,
        onFinish: (Color) -> Unit
    ) {
        while (true) {
            if (!successBlackTurn(getStone, onMoveFail, onMove)) successBlackTurn(getStone, onMoveFail, onMove)
            if (isVictory(State.BLACK, onFinish)) break

            if (!successWhiteTurn(getStone, onMoveFail, onMove)) successWhiteTurn(getStone, onMoveFail, onMove)
            if (isVictory(State.WHITE, onFinish)) break
        }
    }

    private fun isVictory(state: State, onFinish: (Color) -> Unit): Boolean {
        if (referee.isWin(omokBoard.board, state)) {
            when (state) {
                State.WHITE -> onFinish(Color.WHITE)
                State.BLACK -> onFinish(Color.BLACK)
                State.EMPTY -> {}
            }
            return true
        }
        return false
    }

    private fun successBlackTurn(
        getStone: () -> Stone,
        onMoveFail: () -> Unit,
        onMove: (Board, State, Stone) -> Unit
    ): Boolean {
        val blackStone = getStone()
        if (!omokBoard.isEmpty(blackStone)) {
            onMoveFail()
            return false
        }

        omokBoard.move(blackStone, State.BLACK)

        if (!referee.checkForbidden(omokBoard, blackStone)) {
            println("3*3 or 4*4")
            return false
        }
        onMove(omokBoard.board, State.WHITE, blackStone)
        return true
    }

    private fun successWhiteTurn(
        getStone: () -> Stone,
        onMoveFail: () -> Unit,
        onMove: (Board, State, Stone) -> Unit
    ): Boolean {
        val whiteStone = getStone()
        if (!omokBoard.isEmpty(whiteStone)) {
            onMoveFail()
            return false
        }
        omokBoard.move(whiteStone, State.WHITE)
        onMove(omokBoard.board, State.BLACK, whiteStone)
        return true
    }
}
