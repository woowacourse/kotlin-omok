package woowacourse.omok.controller

import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.GameEventListener
import woowacourse.omok.model.StoneState
import woowacourse.omok.model.Stones
import woowacourse.omok.view.OutputView
import woowacourse.omok.view.OutputView.printForbiddenStone

class OmokController(
    private val board: Board = Board(Stones()),
) {
    var gameEventListener: GameEventListener? = null
    var gameEnded: Boolean = false
        private set

    fun start() {
        OutputView.printStart()
    }

    fun getNextTurn(): Color {
        return board.getNextTurn()
    }

    fun placeStoneAtPosition(row: Int, col: Int): Boolean {
        val nextTurn = board.getNextTurn()
        val state = board.takeTurn(nextTurn, row, col)
        checkForbiddenMove(state)
        val isGameEnd = checkGameFinished()
        showPresentBoardStatus(nextTurn, isGameEnd)
        return checkPlacementSuccess(state)
    }

    private fun checkForbiddenMove(state: StoneState) {
        printForbiddenStone(state)
        if (state == StoneState.FORBIDDEN) {
            gameEventListener?.onForbiddenStone()
        }
    }

    private fun checkGameFinished(): Boolean {
        return if (!board.isPlaying()) {
            gameEnded = true
            gameEventListener?.onGameEnd(board.getWinner())
            displayWinner()
            true
        } else {
            false
        }
    }

    private fun showPresentBoardStatus(
        nextTurn: Color,
        isGameEnd: Boolean,
    ) {
        OutputView.printBoard(board.stones)
        if (!isGameEnd) {
            OutputView.printTurnName(nextTurn)
            OutputView.printLastStone(board.stones.getLastStoneCoordinate())
        }
    }

    private fun checkPlacementSuccess(stoneState: StoneState): Boolean {
        return when (stoneState) {
            StoneState.PLACED -> true
            StoneState.FORBIDDEN -> false
            StoneState.OCCUPIED -> false
            StoneState.BEFORE_PLACED -> false
            StoneState.OUTSIDE_THE_BOARD -> false
        }
    }

    fun restartGame() {
        board.resetBoard()
        gameEnded = false
    }

    private fun displayWinner() {
        runCatching {
            OutputView.printWinner(board.getWinner())
        }.onFailure { error ->
            OutputView.printErrorMessage(error.message!!)
        }
    }
}
