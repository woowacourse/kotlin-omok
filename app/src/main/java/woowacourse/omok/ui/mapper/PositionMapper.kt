package woowacourse.omok.ui.mapper

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.ui.model.PositionUiModel

fun PositionUiModel.toPosition(board: Board) =
    Position(
        column,
        row,
        board,
    )
