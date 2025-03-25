package omok.controller

import omok.domain.board.OmokBoard
import omok.domain.rule.OmokRule
import omok.domain.stone.Black
import omok.domain.stone.Stone
import omok.global.retryWhenException
import omok.global.retryWhenNull
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val omokBoard: OmokBoard,
    private val omokRule: OmokRule,
) {
    fun startGame() {
        outputView.printStartMessage()
        var stone: Stone = retryWhenException { Black(getInputPoint()) }
        while (omokBoard.isNotFull()) {
            omokBoard.addStone(stone)
            if (omokRule.isOmok(stone, omokBoard)) {
                outputView.printPrintWinner(stone)
                break
            }
            stone = retryWhenException { stone.toggle(getInputPoint()) }
        }
    }

    private fun getInputPoint(): String {
        outputView.printBoard(omokBoard)
        return retryWhenNull {
            inputView.readStoneWithLastPosition(omokBoard.latestStone)
        }
    }
}
