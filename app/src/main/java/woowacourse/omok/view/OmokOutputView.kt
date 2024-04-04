package woowacourse.omok.view

import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStone

interface OmokOutputView {
    fun showStartMessage()

    fun showProgress(stone: OmokStone?)

    fun showProgressWithBoard(
        board: Board,
        stone: OmokStone?,
    )

    fun showGameResult(
        board: Board,
        stone: OmokStone,
    )

    fun resetView()
}
