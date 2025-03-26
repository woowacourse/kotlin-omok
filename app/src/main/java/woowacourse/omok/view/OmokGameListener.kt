package woowacourse.omok.view

import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor

interface OmokGameListener {
    fun onStartGame()

    fun onBoardUpdated(
        point: Point,
        color: StoneColor,
    )

    fun onGameWon(winnerState: StoneColor?)

    fun onError(message: String)
}
