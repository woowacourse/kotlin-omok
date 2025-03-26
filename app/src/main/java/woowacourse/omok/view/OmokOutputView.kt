package woowacourse.omok.view

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point

interface OmokOutputView {
    fun printStartMessage()

    fun printCurrentTurn(previousPoint: Point?)

    fun printWinColor(previousPoint: Point)

    fun printBoardStatus(board: Board)

    fun printErrorMessage(message: String)
}
