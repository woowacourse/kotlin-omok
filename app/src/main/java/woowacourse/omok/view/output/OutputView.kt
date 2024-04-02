package woowacourse.omok.view.output

import woowacourse.omok.model.board.Board

interface OutputView {
    fun printTurn(board: Board)

    fun printWinner(stoneColor: String?)

    fun printAlert(message: String)
}
