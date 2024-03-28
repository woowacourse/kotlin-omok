package woowacourse.omok.src.main.kotlin.omok.controller

import woowacourse.omok.src.main.kotlin.omok.model.OmokGame
import woowacourse.omok.src.main.kotlin.omok.model.stone.BlackStone
import woowacourse.omok.src.main.kotlin.omok.model.stone.WhiteStone
import woowacourse.omok.src.main.kotlin.omok.view.InputView
import woowacourse.omok.src.main.kotlin.omok.view.OutputView

class OmokController(private val inputView: InputView, private val outputView: OutputView) {
    fun start() {
        val omokGame = readyOmokGame()

        omokGame.start(
            { inputView.readPosition(it) },
            { outputView.drawBoard() },
            { outputView.printWinner(it) },
        )
    }

    private fun readyOmokGame(): OmokGame {
        outputView.printStartGameComment()
        outputView.drawBoard()
        return OmokGame(BlackStone, WhiteStone)
    }
}
