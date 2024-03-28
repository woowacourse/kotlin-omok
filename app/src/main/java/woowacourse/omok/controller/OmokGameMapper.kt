package woowacourse.omok.controller

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import woowacourse.omok.model.BoardUiModel
import woowacourse.omok.model.PositionUiModel
import woowacourse.omok.model.StoneColorUiModel
import woowacourse.omok.model.StoneUiModel

fun PositionUiModel.toPosition(): Position {
    return Position.of(x, y)
}

fun StoneColor.toUiModel(): StoneColorUiModel {
    return when (this) {
        StoneColor.BLACK -> StoneColorUiModel.BLACK
        StoneColor.WHITE -> StoneColorUiModel.WHITE
        StoneColor.NONE -> StoneColorUiModel.EMPTY
    }
}

fun OmokStone.toUiModel(): StoneUiModel {
    val (position, stoneColor) = this
    return StoneUiModel(position.x - 1, position.y - 1, stoneColor.toUiModel())
}

fun Board.toUiModel(): BoardUiModel {
    return toList()
        .map { it.toUiModel() }
        .let(::BoardUiModel)
}

private fun List<OmokStone?>.toUiModel(): Set<StoneUiModel> {
    return mapNotNull {
        it?.toUiModel()
    }.toSet()
}

private fun List<List<OmokStone?>>.rotateLeft(): List<List<OmokStone?>> {
    return List(size) { i ->
        List(size) { j ->
            this[j][size - 1 - i]
        }
    }
}
