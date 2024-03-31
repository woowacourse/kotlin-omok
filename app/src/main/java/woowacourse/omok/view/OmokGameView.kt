package woowacourse.omok.view

import omock.model.board.Block
import omock.model.board.OmokBoard

interface OmokGameView {
    fun showGameStart(initialBoard: OmokBoard)

    fun showCurrentGameState(
        board: OmokBoard,
        stone: Block,
    )

    fun showGameResult(
        board: OmokBoard,
        stone: Block,
    )

    fun showPlaceError(errorMessage: String)

    fun resetView()
}
