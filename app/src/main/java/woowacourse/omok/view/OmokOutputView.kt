package omok.view

import omok.model.Board
import omok.model.OmokStone

interface OmokOutputView {
    fun showStartMessage()

    fun showProgress(
        board: Board,
        stone: OmokStone?,
    )

    fun showGameResult(
        board: Board,
        stone: OmokStone,
    )
}
