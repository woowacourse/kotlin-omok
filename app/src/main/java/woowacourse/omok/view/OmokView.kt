package woowacourse.omok.view

import woowacourse.omok.model.Board
import woowacourse.omok.model.Position

interface OmokView {
    fun updateBoard(
        position: Position,
        board: Board,
    )
}
