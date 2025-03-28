package woowacourse.omok.view

import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.PlaceStoneResult

interface OmokGameListener {
    fun onBoardUpdated(
        point: Point,
        state: CellState,
    )

    fun onGameWon(winnerState: CellState?)

    fun onShowMessage(result: PlaceStoneResult)
}
