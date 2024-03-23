package omok.view

import omok.model.Board
import omok.model.OmokStone
import omok.model.Position

interface OmokView {
    fun showStartMessage()

    fun showProgress(
        board: Board,
        stone: OmokStone?,
    )

    fun readPosition(): Position

    fun showGameResult(
        board: Board,
        stone: OmokStone,
    )
}
