package woowacourse.omok.view

import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor
import woowacourse.omok.domain.board.result.PlaceStoneResult

interface OmokGameListener {
    fun onBoardUpdated(
        point: Point,
        color: StoneColor,
    )

    fun onGameWon(winnerState: StoneColor?)

    fun onShowMessage(result: PlaceStoneResult)
}
