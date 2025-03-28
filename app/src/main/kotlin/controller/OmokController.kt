package controller

import view.InputView
import view.OutputView
import woowacourse.omok.domain.OmokAdapter
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.PutStoneResult
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneState

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private val board = OmokBoard(rule = OmokAdapter())
    private val omokGame = OmokGame(board)

    fun start() {
        outputView.printStartMessage()
        playGame()
    }

    private fun playGame() {
        var latestPosition: Position? = null
        while (true) {
            val nowTurn = omokGame.turn
            printGameStatus(nowTurn)
            latestPosition = inputView.getPosition(latestPosition)
            val stone = Stone(latestPosition, nowTurn)
            when (val putResult = omokGame.putStone(stone)) {
                is PutStoneResult.NextTurn -> {
                    omokGame.changeTurn()
                }

                is PutStoneResult.Finished -> {
                    printGameResult(board, putResult.turn)
                    return
                }

                else -> outputView.printError(putResult)
            }
        }
    }

    private fun printGameStatus(nowTurn: StoneState) {
        outputView.printBoardState(board)
        outputView.printTurn(nowTurn)
    }

    private fun printGameResult(
        board: OmokBoard,
        turn: StoneState,
    ) {
        outputView.printBoardState(board)
        outputView.printWinner(turn)
    }
}
