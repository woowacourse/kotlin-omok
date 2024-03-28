package woowacourse.omok.view

import omok.model.Board
import omok.model.Position

interface OmokView {
    fun updateBoard(
        position: Position,
        board: Board,
    )
}
