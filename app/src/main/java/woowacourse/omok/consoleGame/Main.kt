package woowacourse.omok.consoleGame

import woowacourse.omok.consoleGame.controller.OmokController
import woowacourse.omok.consoleGame.view.OmokView
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.board.Board

fun main() {
    val omokView = OmokView()
    val board = Board()
    val omokGame = OmokGame(board)
    OmokController(omokView, omokGame).run()
}
