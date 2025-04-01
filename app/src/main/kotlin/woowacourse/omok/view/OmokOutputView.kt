package woowacourse.omok.view

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.PlaceStoneResult

interface OmokOutputView {
    fun printStartMessage()

    fun printCurrentTurn(previousPoint: Pair<Point?, CellState>)

    fun printWinColor(winnerState: CellState?)

    fun printBoardStatus(board: Board)

    fun printMessage(result: PlaceStoneResult)
}
