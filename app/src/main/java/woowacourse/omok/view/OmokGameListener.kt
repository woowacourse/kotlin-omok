package woowacourse.omok.view

import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor

interface OmokGameListener {
    fun onBoardUpdated(
        point: Point,
        color: StoneColor,
    )

    fun onGameWon(winnerState: StoneColor?)

    fun onError(message: String)
}
