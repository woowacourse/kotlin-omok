package omok.controller

import omok.model.Position
import omok.view.model.PositionUiModel

fun PositionUiModel.toPosition(): Position {
    return Position.of(x, y)
}
