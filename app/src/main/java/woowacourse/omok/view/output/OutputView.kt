package woowacourse.omok.view.output

import woowacourse.omok.model.board.Board

interface OutputView {
    fun printStartGuide()

    fun printTurn(board: Board)

    fun printWinner(board: Board)

    fun printInAppropriatePlace(message: String)
}
