package woowacourse.ui.mapper

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.ui.model.PositionUiModel

fun PositionUiModel.toPosition(board: Board) =
    Position(
        column,
        row,
        board,
    )
