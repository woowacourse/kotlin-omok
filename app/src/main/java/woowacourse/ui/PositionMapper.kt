package woowacourse.ui

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position

fun PositionUiModel.toPosition(board: Board) =
    Position(
        column,
        row,
        board,
    )
