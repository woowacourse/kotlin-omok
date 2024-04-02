package woowacourse.omok.controller

import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.OmokGame
import woowacourse.omok.view.OutputView

object Controller {
    private val outputView = OutputView()
    val omok = OmokGame(outputView)

    fun start(currentStone: Stone) {
        omok.endGame(omok.startGame(currentStone))
    }
}
