package woowacourse.omok.controller

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.player.Turn
import woowacourse.omok.view.OmokView

class OmokController(
    private val omokView: OmokView,
) {
    fun run() {
        omokView.printStartMessage()
        val omokBoard = OmokBoard()
        playGame(omokBoard)
    }

    private fun playGame(omokBoard: OmokBoard) {
        val turn = Turn()
        while (turn.playing()) {
            playerTurn(turn, omokBoard)
            if (finishGame(turn, omokBoard)) break
            omokView.printOmokBoard(omokBoard.board)
            turn.next()
        }
    }

    private fun handleTurnException(inputStone: () -> Unit) {
        runCatching {
            inputStone()
        }.onFailure { error ->
            println(error.message)
            handleTurnException(inputStone)
        }
    }

    private fun playerTurn(
        turn: Turn,
        omokBoard: OmokBoard,
    ) {
        handleTurnException {
            val position = omokView.inputPosition(turn.stone)
            turn.place(position, omokBoard)
        }
    }

    private fun finishGame(
        turn: Turn,
        omokBoard: OmokBoard,
    ): Boolean {
        if (turn.win()) {
            omokView.printOmokBoard(omokBoard.board)
            omokView.result(turn.stone)
            return true
        }
        return false
    }
}
