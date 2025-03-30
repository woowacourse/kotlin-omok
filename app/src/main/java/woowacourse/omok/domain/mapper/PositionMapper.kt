package woowacourse.omok.domain.mapper

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.ui.PositionUiModel

fun PositionUiModel.toPosition(board: Board) =
    Position(
        column,
        row,
        board,
    )
