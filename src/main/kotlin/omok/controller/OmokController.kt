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
        var stone: Stone = retryWhenFailedToAddStone()
        while (omokBoard.isNotFull() || isFinished(stone)) {
            stone =
                retryWhenFailedToAddStone {
                    stone.toggle(getInputPoint())
                }
        }
    }

    private fun isFinished(stone: Stone): Boolean {
        if (omokRule.isOmok(stone, omokBoard)) {
            outputView.printPrintWinner(stone)
            return true
        }
        return false
    }

    private fun retryWhenFailedToAddStone(action: () -> Stone = { Black(getInputPoint()) }): Stone {
        return retryWhenException {
            val stone = action()
            omokBoard.addStone(stone)
            stone
        }
    }

    private fun getInputPoint(): String {
        outputView.printBoard(omokBoard)
        return retryWhenNull {
            inputView.readStoneWithLastPosition(omokBoard.latestStone)
        }
    }
}
