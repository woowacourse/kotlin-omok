package woowacourse.omok

import woowacourse.omok.controller.ConsoleOmokControl
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.view.consoleView.InputView
import woowacourse.omok.view.consoleView.OutputView

fun main() {
    val boardSize = BoardSize(15)
    val inputView = InputView()
    val outputView = OutputView(boardSize)

    ConsoleOmokControl(inputView, outputView, boardSize).run()
}
